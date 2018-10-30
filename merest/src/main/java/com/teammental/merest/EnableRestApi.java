package com.teammental.merest;

import com.teammental.merest.autoconfiguration.RestApiApplicationConfigurationProperties;
import com.teammental.merest.autoconfiguration.RestApiProxyBeansRegistrar;
import com.teammental.merest.autoconfiguration.RestApiProxyConfiguration;
import com.teammental.merest.autoconfiguration.StartupApplicationConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

/**
 * Enables {@link com.teammental.mecore.stereotype.controller.RestApi}
 * proxy bean auto-configuration.
 * You should use this annotation on a Spring configuration class.
 * Proxy beans for {@link com.teammental.mecore.stereotype.controller.RestApi} annotated
 * interfaces will be created automatically.
 *
 * @since 1.2.3
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({StartupApplicationConfiguration.class,
    RestApiProxyConfiguration.class,
    RestApiProxyBeansRegistrar.class,
    RestApiApplicationConfigurationProperties.class})
public @interface EnableRestApi {

  /**
   * Names of packages to be scanned for RestApi's
   *
   * @return package names
   */
  @AliasFor("basePackages")
  String[] value() default {};

  /**
   * @see #value()
   */
  @AliasFor("value")
  String[] basePackages() default {};
}
