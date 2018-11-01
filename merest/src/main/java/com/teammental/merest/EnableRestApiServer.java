package com.teammental.merest;

import com.teammental.merest.autoconfiguration.FilterDtoConverterRegistrar;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({
    FilterDtoConverterRegistrar.class
})
public @interface EnableRestApiServer {
}
