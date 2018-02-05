package com.teammental.mevalidation.constraint;

import com.teammental.mecore.enums.FileExtension;
import com.teammental.mevalidation.constraint.validator.MultipartFileConstraintSingleValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MultipartFileConstraintSingleValidator.class)
public @interface MultipartFileConstraint {
  /**
   * Default validation error message.
   * @return message string
   */
  String message() default "{portalbase.validation.multipartfile.default}";

  /**
   * See {@link Constraint}.
   * @return payload.
   */
  Class<?>[] groups() default {};

  /**
   * See {@link Constraint}.
   * @return payload.
   */
  Class<? extends Payload>[] payload() default {};

  /**
   * Maximum length of file size, in bytes.
   * @return length value
   */
  long maxFileSize();

  /**
   * Sets if the MultipartFile property is required.
   * @return true if required
   */
  boolean required() default false;

  /**
   * Allowed file extensions.
   * @return Array of FileExtensions
   */
  FileExtension[] fileExtensions() default {};
}
