package com.teammental.mevalidation.constraint;

import com.teammental.mecore.enums.ImageColorType;
import com.teammental.mevalidation.constraint.validator.MultipartFileImageConstraintSingleValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MultipartFileImageConstraintSingleValidator.class)
public @interface MultipartFileImageConstraint {

  /**
   * Default validation error message.
   *
   * @return message string
   */
  String message() default "{portalbase.validation.multipartfile.default}";

  /**
   * See {@link Constraint}.
   *
   * @return payload.
   */
  Class<?>[] groups() default {};

  /**
   * See {@link Constraint}.
   *
   * @return payload.
   */
  Class<? extends Payload>[] payload() default {};

  /**
   * Requested DPI value of the image file.
   * Defaults to 0, meaning no dpi constraint.
   *
   * @return DPI value
   */
  int dpi() default 0;

  /**
   * Image's color type constraint.
   *
   * @return color type
   */
  ImageColorType colorType() default ImageColorType.OTHER;

  /**
   * Image's maximum height constraint.
   * Defaults to 0, meaning no height limit.
   *
   * @return max height
   */
  int maxHeight() default 0;

  /**
   * Image's minimum height constraint.
   * Defaults to 0, meaning no height limit.
   *
   * @return min height
   */
  int minHeight() default 0;

  /**
   * Image's maximum width constraint.
   * Defaults to 0, meaning no width limit.
   *
   * @return max width
   */
  int maxWidth() default 0;

  /**
   * Image's minimum width constraint.
   * Defaults to 0, meaning no width limit.
   *
   * @return min width
   */
  int minWidth() default 0;

}
