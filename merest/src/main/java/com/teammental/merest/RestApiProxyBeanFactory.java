package com.teammental.merest;

import com.teammental.mecore.stereotype.controller.RestApi;
import com.teammental.mecore.stereotype.controller.RestApiProxy;
import com.teammental.mehelper.StringHelper;
import com.teammental.merest.autoconfiguration.RestApiApplication;
import com.teammental.merest.autoconfiguration.RestApiApplicationRegistry;
import com.teammental.merest.exception.ApplicationNameCannotBeNullOrEmptyException;
import com.teammental.merest.exception.RestApiApplicationIsNotRegisteredException;
import com.teammental.merest.exception.RestApiProxyAnnotationIsMissingException;
import com.teammental.merest.util.RestApiProxyUtil;
import java.lang.reflect.Proxy;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class RestApiProxyBeanFactory {

  private static final String MOCK_IMPL_SUFFIX = "MockImpl";

  private static final Logger LOGGER = LoggerFactory.getLogger(RestApiProxyBeanFactory.class);

  private RestApiProxyHandler restApiProxyHandler;

  @Autowired
  private RestApiApplicationRegistry restApiApplicationRegistry;

  @Autowired
  public RestApiProxyBeanFactory(RestApiProxyHandler restApiProxyHandler) {
    this.restApiProxyHandler = restApiProxyHandler;
  }

  /**
   * Creates a proxy bean implementation for given RestApiProxy interface type.
   *
   * @param classLoader     class loader
   * @param clazz           class
   * @param <RestApiProxyT> RestApi type
   * @return proxy implementation
   */
  @SuppressWarnings("unchecked")
  public <RestApiProxyT> RestApiProxyT createRestApiProxyBean(ClassLoader classLoader,
                                                              Class<RestApiProxyT> clazz) {


    if (!clazz.isAnnotationPresent(RestApiProxy.class)
        && !clazz.isAnnotationPresent(RestApi.class)) {
      throw new RestApiProxyAnnotationIsMissingException(clazz);
    }

    String applicationName = RestApiProxyUtil.getRestApiApplicationName(clazz);

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
        return (RestApiProxyT) implInstance;
      } catch (Exception ex) {
        LOGGER.debug(ex.getLocalizedMessage());
      }
    }

    return (RestApiProxyT) Proxy.newProxyInstance(classLoader,
        new Class[]{clazz},
        restApiProxyHandler);
  }
}
