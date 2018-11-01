package com.teammental.merest;

import com.teammental.merest.autoconfiguration.FilterDtoConverterRegistrar;
import com.teammental.merest.autoconfiguration.RestApiApplicationConfigurationProperties;
import com.teammental.merest.autoconfiguration.RestApiApplicationRegistry;
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
 * Enables {@link com.teammental.mecore.stereotype.controller.RestApiProxy}
 * proxy bean auto-configuration.
 * You should use this annotation on a Spring configuration class.
 * Proxy beans for {@link com.teammental.mecore.stereotype.controller.RestApiProxy} annotated
 * interfaces will be created automatically.
 *
 * @since 1.4.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({RestApiApplicationRegistry.class,
    StartupApplicationConfiguration.class,
    RestApiApplicationConfigurationProperties.class,
    RestApiProxyConfiguration.class,
    RestApiProxyBeansRegistrar.class,
    FilterDtoConverterRegistrar.class})
public @interface EnableRestApiProxy {

  /**
   * Names of packages to be scanned for RestApiProxy's.
   *
   * @return package names
   */
  @AliasFor("basePackages")
  String[] value() default {};

  /**
   * Names of packages to be scanned for RestApiProxy's.
   *
   * @return package names.
   * @see #value() .
   */
  @AliasFor("value")
  String[] basePackages() default {};
}
