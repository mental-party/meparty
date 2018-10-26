package com.teammental.merest;

import com.teammental.mecore.stereotype.controller.Controller;
import com.teammental.mecore.stereotype.controller.RestApi;
import com.teammental.mehelper.AssertHelper;
import com.teammental.mehelper.StringHelper;
import com.teammental.merest.autoconfiguration.ApplicationExplorer;
import com.teammental.merest.exception.ApplicationNameCannotBeNullOrEmptyException;
import com.teammental.merest.exception.RestApiAnnotationIsMissingException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @see EnableRestApi.
 * @deprecated since 1.2.4.
 * Use {@link EnableRestApi} to enable auto-configuration for {@link RestApi} interfaces.
 */
@Deprecated
public class RestApiProxyFactory {

  private static final String MOCK_IMPL_SUFFIX = "MockImpl";
  private static final Logger LOGGER = LoggerFactory.getLogger(RestApiProxyFactory.class);

  /**
   * Returns Rest API implementation.
   *
   * @param restApiClass type of Rest API
   * @param <T>          Rest Api type
   * @return Rest API instance
   */
  @SuppressWarnings("unchecked")
  public static <T extends Controller> T createProxy(Class<T> restApiClass) {
    AssertHelper.notNull(restApiClass);

    if (!restApiClass.isAnnotationPresent(RestApi.class)) {
      throw new RestApiAnnotationIsMissingException(restApiClass);
    }

    RestApi restApiAnnotation = restApiClass.getAnnotation(RestApi.class);
    String applicationName = restApiAnnotation.value();

    if (StringHelper.isNullOrEmpty(applicationName)) {
      throw new ApplicationNameCannotBeNullOrEmptyException(restApiClass);
    }

    if (ApplicationExplorer.getInstance().getUseMockImpl()) {
      Package pack = restApiClass.getPackage();
      String implName = pack.getName() + "." + restApiClass.getSimpleName() + MOCK_IMPL_SUFFIX;

      try {
        Class<?> implClass = Class.forName(implName);
        Object implInstance = implClass.newInstance();
        return (T) implInstance;
      } catch (Exception ex) {
        LOGGER.debug(ex.getLocalizedMessage());
      }
    }

    InvocationHandler handler = new RestApiProxyInvocationHandler(restApiClass);

    T restApi = (T) Proxy.newProxyInstance(restApiClass.getClassLoader(),
        new Class[]{restApiClass},
        handler);

    return restApi;
  }
}