package com.teammental.merest;

import com.teammental.merest.autoconfiguration.RestApiProxyBeansRegistrar;
import com.teammental.merest.autoconfiguration.RestApiProxyConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

/**
 * Enables {@link com.teammental.mecore.stereotype.controller.RestApi}
 * proxy bean auto-configuration.
 * <p>
 * You should use this annotation on a Spring configuration class.
 * <p>
 * Proxy beans for {@link com.teammental.mecore.stereotype.controller.RestApi} annotated
 * interfaces will be created automatically.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({RestApiProxyConfiguration.class,
    RestApiProxyBeansRegistrar.class})
public @interface EnableRestApi {

  @AliasFor("basePackages")
  String[] value() default {};

  @AliasFor("value")
  String[] basePackages() default {};
}
