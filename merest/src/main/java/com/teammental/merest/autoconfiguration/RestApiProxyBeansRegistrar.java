package com.teammental.merest.autoconfiguration;

import com.teammental.mecore.stereotype.controller.RestApi;
import com.teammental.merest.EnableRestApi;
import com.teammental.merest.RestApiAnnotationClassPathScanner;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.MultiValueMap;

@Configuration
public class RestApiProxyBeansRegistrar
    implements ImportBeanDefinitionRegistrar,
    BeanClassLoaderAware {

  private static final Logger LOGGER
      = LoggerFactory.getLogger(RestApiProxyBeansRegistrar.class);

  private RestApiAnnotationClassPathScanner restApiAnnotationClassPathScanner;
  private ClassLoader classLoader;

  public RestApiProxyBeansRegistrar() {
    restApiAnnotationClassPathScanner = new RestApiAnnotationClassPathScanner(false);
    restApiAnnotationClassPathScanner.addIncludeFilter(new AnnotationTypeFilter(RestApi.class));
  }

  @Override
  public void setBeanClassLoader(ClassLoader classLoader) {
    this.classLoader = classLoader;
  }

  @Override
  public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                      BeanDefinitionRegistry registry) {

    String[] basPackages
        = getBasePackages(importingClassMetadata);

    if (basPackages != null
        && basPackages.length > 0) {
      for (String basePackage
          : basPackages) {
        createRestApiProxies(basePackage, registry);
      }
    }

  }

  private String[] getBasePackages(AnnotationMetadata importingClassMetadata) {
    String[] basePackages = null;

    MultiValueMap<String, Object> allAnnotationAttributes
        = importingClassMetadata
        .getAllAnnotationAttributes(EnableRestApi.class.getName());

    if (allAnnotationAttributes != null
        && allAnnotationAttributes.containsKey("basePackages")) {
      basePackages = (String[]) allAnnotationAttributes.getFirst("basePackages");
    }

    if (basePackages == null
        || basePackages.length == 0) {
      String importingClassPackageName
          = ClassUtils.getPackageName(importingClassMetadata.getClassName());
      basePackages = new String[]{importingClassPackageName};
    }

    return basePackages;

    //https://stackoverflow.com/questions/39507736/dynamic-proxy-bean-with-autowiring-capability?noredirect=1&lq=1
  }

  private void createRestApiProxies(String basePackage,
                                    BeanDefinitionRegistry registry) {
    try {
      Set<BeanDefinition> beanDefinitions
          = restApiAnnotationClassPathScanner.findCandidateComponents(basePackage);
      for (BeanDefinition beanDefinition
          : beanDefinitions) {

        Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());

        String beanName
            = ClassUtils.getShortNameAsProperty(clazz);

        GenericBeanDefinition proxyBeanDefinition
            = new GenericBeanDefinition();
        proxyBeanDefinition.setBeanClass(clazz);

        ConstructorArgumentValues constructorArgumentValues
            = new ConstructorArgumentValues();

        constructorArgumentValues.addGenericArgumentValue(classLoader);
        constructorArgumentValues.addGenericArgumentValue(clazz);

        proxyBeanDefinition.setConstructorArgumentValues(constructorArgumentValues);
        proxyBeanDefinition.setFactoryBeanName("restApiProxyBeanFactory");
        proxyBeanDefinition.setFactoryMethodName("createRestApiProxyBean");

        registry.registerBeanDefinition(beanName, proxyBeanDefinition);

      }
    } catch (Exception ex) {
      LOGGER.debug(ex.getLocalizedMessage());
    }
  }
}
