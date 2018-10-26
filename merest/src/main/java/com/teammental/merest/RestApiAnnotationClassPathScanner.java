package com.teammental.merest;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;

public class RestApiAnnotationClassPathScanner
    extends ClassPathScanningCandidateComponentProvider {

  public RestApiAnnotationClassPathScanner(final boolean useDefaultFilters) {
    super(useDefaultFilters);
  }

  @Override
  protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
    return beanDefinition.getMetadata().isIndependent();
  }
}
