package com.teammental.merest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teammental.mecore.stereotype.controller.RestApi;
import com.teammental.medto.FilterDto;
import com.teammental.mehelper.CastHelper;
import com.teammental.mehelper.PrimitiveHelper;
import com.teammental.mehelper.StringHelper;
import com.teammental.merest.exception.NoRequestMappingFoundException;
import com.teammental.mevalidation.dto.ValidationResultDto;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

class RestApiProxyInvocationHandler
    implements InvocationHandler {

  private Class<?> restApiClass;

  public RestApiProxyInvocationHandler(Class<?> restApiClass) {
    this.restApiClass = restApiClass;
  }

  private static final Logger LOGGER = LoggerFactory.getLogger(RestApiProxyInvocationHandler.class);

  private ApplicationExplorer applicationExplorer = ApplicationExplorer.getInstance();

  private RestTemplate restTemplate = new RestTemplate();

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

    RestExchangeProperties properties = prepareRestExchangeProperties(proxy, method, args);

    RestResponse<Object> restResponse;
    try {

      restResponse = doRestExchange(properties);

    } catch (HttpStatusCodeException exception) {

      restResponse = handleHttpStatusCodeException(exception);
    } catch (Exception exception) {

      HttpStatusCodeException statusCodeException =
          new HttpServerErrorException(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage());

      restResponse = handleHttpStatusCodeException(statusCodeException);
    }

    return restResponse;
  }

  RestExchangeProperties prepareRestExchangeProperties(Object proxy, Method method, Object[] args) {

    RestApi restApiAnnotation = AnnotationUtils.findAnnotation(proxy.getClass(), RestApi.class);
    String applicationName = restApiAnnotation.value();

    String url = applicationExplorer.getApplication(applicationName);

    Mapping classLevelMapping = extractMapping(proxy.getClass());
    String classLevelUrl = classLevelMapping.getUrl();

    Mapping methodLevelMapping = extractMapping(method);
    String methodLevelUrl = methodLevelMapping.getUrl();

    url = url + classLevelUrl + methodLevelUrl;

    HttpMethod httpMethod = methodLevelMapping.getHttpMethod();

    Map<String, Object> urlVariables = new HashMap<>();
    Object requestBody = null;
    Parameter[] parameters = method.getParameters();
    for (int i = 0; i < parameters.length; i++) {
      Parameter parameter = parameters[i];
      Object value = args[i];

      if (parameter.isAnnotationPresent(PathVariable.class)) {
        PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);
        String name = pathVariable.value();
        urlVariables.put(name, value);
      } else if (parameter.isAnnotationPresent(RequestParam.class)) {
        RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
        String name = requestParam.value();
        urlVariables.put(name, value);
      } else if (parameter.isAnnotationPresent(RequestBody.class)) {

        if (parameter.getType().isAssignableFrom(FilterDto.class)
            && value != null) {
          FilterDtoWrapper filterDtoWrapper = new FilterDtoWrapper();
          filterDtoWrapper.setFilterDto(value);
          filterDtoWrapper.setFilterDtoType(value.getClass());
          requestBody = filterDtoWrapper;
        } else {

          requestBody = value;
        }
      }
    }

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(methodLevelMapping.getMediaType());

    HttpEntity httpEntity = requestBody == null ? null : new HttpEntity<>(requestBody, headers);

    Class<?> parameterizedReturnType = extractReturnType(method.getGenericReturnType(),
        true, restApiClass);
    Class<?> rowReturnType = extractReturnType(method.getGenericReturnType(),
        false, restApiClass);


    RestExchangeProperties properties = new RestExchangeProperties(url,
        httpMethod, httpEntity, urlVariables, parameterizedReturnType, rowReturnType);

    return properties;
  }

  RestResponse<Object> handleHttpStatusCodeException(HttpStatusCodeException exception) {

    HttpStatus status = exception.getStatusCode();

    RestResponse<Object> restResponse = new RestResponse<>(status);

    ObjectMapper objectMapper = new ObjectMapper();

    ValidationResultDto validationResultDto;

    try {

      validationResultDto = objectMapper
          .readValue(exception.getResponseBodyAsString(), ValidationResultDto.class);

    } catch (Exception ex) {
      LOGGER.error(ex.getLocalizedMessage());

      validationResultDto = null;
    }

    if (validationResultDto == null) {
      restResponse.setResponseMessage(exception.getResponseBodyAsString());
    } else {
      restResponse.setValidationResult(validationResultDto);
    }

    return restResponse;
  }

  RestResponse<Object> doRestExchange(RestExchangeProperties properties)
      throws IOException, HttpStatusCodeException {

    RestResponse<Object> restResponse;

    ResponseEntity<String> responseEntity = restTemplate.exchange(properties.getUrl(),
        properties.getHttpMethod(), properties.getHttpEntity(),
        new ParameterizedTypeReference<String>() {
        }, properties.getUrlVariables());

    Object returnBody = null;

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper = objectMapper.disable(
        DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)
        .disable(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE)
        .disable(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES)
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .disable(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY)
        .disable(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES)
        .disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
        .disable(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES)
        .disable(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS)
        .disable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY)
        .disable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)
        .disable(DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS);

    String body = responseEntity.getBody();

    if (!StringHelper.isNullOrEmpty(responseEntity.getBody())) {
      if (responseEntity.getBody().startsWith("[")) {
        returnBody = objectMapper.readValue(body,
            objectMapper.getTypeFactory().constructCollectionType(List.class,
                properties.getRowReturnType()));
      } else {

        boolean isPage = properties.getPageReturnType() != null
            && properties.getPageReturnType().equals(RestResponsePageImpl.class);

        if (isPage) {
          body = body.replace("\"pageable\":\"INSTANCE\",", "");

          JavaType actualType = objectMapper.getTypeFactory()
              .constructParametricType(properties.getPageReturnType(),
                  properties.getRowReturnType());

          returnBody = objectMapper
              .readValue(body, actualType);
        } else {

          if (PrimitiveHelper
              .isWrapperOrPrimitive(properties.getRowReturnType())) {

            // do not use object mapper
            // if returned value is primitive or wrapper type
            returnBody = CastHelper
                .castFromString(body, properties.getRowReturnType());

          } else {
            returnBody = objectMapper
                .readValue(body, properties.getRowReturnType());
          }

        }
      }
    }
    restResponse = new RestResponse<>(returnBody,
        responseEntity.getHeaders(),
        responseEntity.getStatusCode());

    return restResponse;
  }

  Class<?> extractReturnType(Type genericReturnType,
                             boolean extractForPageType,
                             Class<?> proxyClass) {

    Type returnType = GenericTypeResolver
        .resolveType(genericReturnType, proxyClass);


    if (returnType instanceof ParameterizedType) {

      ParameterizedType parameterizedType = (ParameterizedType) returnType;

      if (extractForPageType && parameterizedType.getRawType().equals(Page.class)) {
        return RestResponsePageImpl.class;
      }

      Type actualType = parameterizedType.getActualTypeArguments()[0];
      return extractReturnType(actualType, extractForPageType, proxyClass);

    }

    return (Class<?>) returnType;

  }

  /**
   * Searches {@link RequestMapping RequestMapping}
   * annotation on the given method argument and extracts
   * If RequestMapping annotation is not found, NoRequestMappingFoundException is thrown.
   * {@link HttpMethod HttpMethod} type equivalent to
   * {@link RequestMethod RequestMethod} type
   *
   * @param element AnnotatedElement object to be examined.
   * @return Mapping object
   */
  Mapping extractMapping(AnnotatedElement element) {

    Annotation annotation = findMappingAnnotation(element);
    String[] urls;
    RequestMethod requestMethod;
    String consumes;

    if (annotation instanceof RequestMapping) {
      RequestMapping requestMapping = (RequestMapping) annotation;
      requestMethod = requestMapping.method().length == 0
          ? RequestMethod.GET : requestMapping.method()[0];
      urls = requestMapping.value();
      consumes = StringHelper.getFirstOrEmpty(requestMapping.consumes());

    } else if (annotation instanceof GetMapping) {

      requestMethod = RequestMethod.GET;
      urls = ((GetMapping) annotation).value();
      consumes = StringHelper.getFirstOrEmpty(((GetMapping) annotation).consumes());

    } else if (annotation instanceof PostMapping) {

      requestMethod = RequestMethod.POST;
      urls = ((PostMapping) annotation).value();
      consumes = StringHelper.getFirstOrEmpty(((PostMapping) annotation).consumes());

    } else if (annotation instanceof PutMapping) {

      requestMethod = RequestMethod.PUT;
      urls = ((PutMapping) annotation).value();
      consumes = StringHelper.getFirstOrEmpty(((PutMapping) annotation).consumes());

    } else if (annotation instanceof DeleteMapping) {

      requestMethod = RequestMethod.DELETE;
      urls = ((DeleteMapping) annotation).value();
      consumes = StringHelper.getFirstOrEmpty(((DeleteMapping) annotation).consumes());

    } else if (annotation instanceof PatchMapping) {

      requestMethod = RequestMethod.PATCH;
      urls = ((PatchMapping) annotation).value();
      consumes = StringHelper.getFirstOrEmpty(((PatchMapping) annotation).consumes());

    } else {
      throw new NoRequestMappingFoundException(element);
    }

    HttpMethod httpMethod = HttpMethod.resolve(requestMethod.name());
    String url = StringHelper.getFirstOrEmpty(urls);

    MediaType mediaType;
    try {
      mediaType = MediaType.valueOf(consumes);
    } catch (InvalidMediaTypeException exception) {
      mediaType = MediaType.APPLICATION_JSON_UTF8;
    }

    return new Mapping(httpMethod, url, mediaType);

  }

  Annotation findMappingAnnotation(AnnotatedElement element) {

    Annotation mappingAnnotation = element.getAnnotation(RequestMapping.class);

    if (mappingAnnotation == null) {
      mappingAnnotation = element.getAnnotation(GetMapping.class);

      if (mappingAnnotation == null) {
        mappingAnnotation = element.getAnnotation(PostMapping.class);

        if (mappingAnnotation == null) {
          mappingAnnotation = element.getAnnotation(PutMapping.class);

          if (mappingAnnotation == null) {
            mappingAnnotation = element.getAnnotation(DeleteMapping.class);

            if (mappingAnnotation == null) {
              mappingAnnotation = element.getAnnotation(PatchMapping.class);
            }
          }
        }
      }
    }

    if (mappingAnnotation == null) {
      if (element instanceof Method) {
        Method method = (Method) element;
        mappingAnnotation = AnnotationUtils.findAnnotation(method, RequestMapping.class);
      } else {
        Class<?> clazz = (Class<?>) element;
        mappingAnnotation = AnnotationUtils.findAnnotation(clazz, RequestMapping.class);
      }
    }

    return mappingAnnotation;
  }

  class RestExchangeProperties {

    private String url;
    private HttpMethod httpMethod;
    private HttpEntity httpEntity;
    private Map<String, Object> urlVariables;
    private Class<?> pageReturnType;
    private Class<?> rowReturnType;

    public RestExchangeProperties(String url,
                                  HttpMethod httpMethod,
                                  HttpEntity httpEntity,
                                  Map<String, Object> urlVariables,
                                  Class<?> pageReturnType,
                                  Class<?> rowReturnType) {

      this.url = url;
      this.httpMethod = httpMethod;
      this.httpEntity = httpEntity;
      this.urlVariables = urlVariables;
      this.pageReturnType = Page.class.isAssignableFrom(pageReturnType) ? pageReturnType : null;
      this.rowReturnType = rowReturnType;
    }

    public String getUrl() {

      return url;
    }

    public HttpMethod getHttpMethod() {

      return httpMethod;
    }

    public HttpEntity getHttpEntity() {

      return httpEntity;
    }

    public Map<String, Object> getUrlVariables() {

      return urlVariables;
    }

    public Class<?> getPageReturnType() {

      return pageReturnType;
    }

    public Class<?> getRowReturnType() {

      return rowReturnType;
    }
  }

  class Mapping {

    private HttpMethod httpMethod;
    private String url;
    private MediaType mediaType;

    public Mapping(HttpMethod httpMethod, String url, MediaType mediaType) {

      this.httpMethod = httpMethod;
      this.url = url;
      this.mediaType = mediaType;
    }

    public HttpMethod getHttpMethod() {

      return httpMethod;
    }

    public String getUrl() {

      return url;
    }

    public MediaType getMediaType() {

      return mediaType;
    }
  }
}