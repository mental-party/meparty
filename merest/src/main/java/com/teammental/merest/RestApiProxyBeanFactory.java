package com.teammental.merest;

import java.lang.reflect.Proxy;
import org.springframework.beans.factory.annotation.Autowired;

public class RestApiProxyBeanFactory {

  private RestApiProxy restApiProxy;

  @Autowired
  public RestApiProxyBeanFactory(RestApiProxy restApiProxy) {
    this.restApiProxy = restApiProxy;
  }

  @SuppressWarnings("unchecked")
  public <RestApiT> RestApiT createRestApiProxyBean(ClassLoader classLoader,
                                                    Class<RestApiT> clazz) {
    return (RestApiT) Proxy.newProxyInstance(classLoader,
        new Class[]{clazz},
        restApiProxy);
  }
}
