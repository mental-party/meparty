package com.teammental.merest;

import com.teammental.mecore.stereotype.controller.RestApi;
import com.teammental.mehelper.StringHelper;
import com.teammental.merest.autoconfiguration.RestApiApplication;
import com.teammental.merest.autoconfiguration.RestApiApplicationRegistry;
import com.teammental.merest.exception.ApplicationNameCannotBeNullOrEmptyException;
import com.teammental.merest.exception.RestApiAnnotationIsMissingException;
import com.teammental.merest.exception.RestApiApplicationIsNotRegisteredException;
import java.lang.reflect.Proxy;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class RestApiProxyBeanFactory {

  private static final String MOCK_IMPL_SUFFIX = "MockImpl";

  private static final Logger LOGGER = LoggerFactory.getLogger(RestApiProxyFactory.class);

  private RestApiProxy restApiProxy;

  @Autowired
  private RestApiApplicationRegistry restApiApplicationRegistry;

  @Autowired
  public RestApiProxyBeanFactory(RestApiProxy restApiProxy) {
    this.restApiProxy = restApiProxy;
  }

  /**
   * Creates a proxy bean implementation for given RestApi interface type.
   *
   * @param classLoader class loader
   * @param clazz       class
   * @param <RestApiT>  RestApi type
   * @return proxy implementation
   */
  @SuppressWarnings("unchecked")
  public <RestApiT> RestApiT createRestApiProxyBean(ClassLoader classLoader,
                                                    Class<RestApiT> clazz) {


    if (!clazz.isAnnotationPresent(RestApi.class)) {
      throw new RestApiAnnotationIsMissingException(clazz);
    }

    RestApi restApiAnnotation = clazz.getAnnotation(RestApi.class);
    String applicationName = restApiAnnotation.value();

    if (StringHelper.isNullOrEmpty(applicationName)) {
      throw new ApplicationNameCannotBeNullOrEmptyException(clazz);
    }

    Optional<RestApiApplication> optionalRestApiApplication
        = restApiApplicationRegistry
        .getRestApiApplication(applicationName);

    if (!optionalRestApiApplication.isPresent()) {
      throw new RestApiApplicationIsNotRegisteredException(applicationName);
    }

    if (optionalRestApiApplication
        .get().getUseMockImpl()) {

      Package pack = clazz.getPackage();
      String implName = pack.getName() + "."
          + clazz.getSimpleName() + MOCK_IMPL_SUFFIX;

      try {
        Class<?> implClass = Class.forName(implName);
        Object implInstance = implClass.newInstance();
        return (RestApiT) implInstance;
      } catch (Exception ex) {
        LOGGER.debug(ex.getLocalizedMessage());
      }
    }

    return (RestApiT) Proxy.newProxyInstance(classLoader,
        new Class[]{clazz},
        restApiProxy);
  }
}
