package com.teammental.mevalidation.constraint.validator;

import com.teammental.mecore.enums.FileExtension;
import com.teammental.mehelper.FileHelper;
import com.teammental.mevalidation.constraint.MultipartFileConstraint;
import java.util.Arrays;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class MultipartFileConstraintSingleValidator
    implements ConstraintValidator<MultipartFileConstraint, MultipartFile> {

  private FileExtension[] fileExtensions;
  private long maxFileSize;
  private boolean required;

  @Override
  public void initialize(MultipartFileConstraint constraintAnnotation) {
    this.fileExtensions = constraintAnnotation.fileExtensions();
    this.maxFileSize = constraintAnnotation.maxFileSize();
    this.required = constraintAnnotation.required();
  }

  @Override
  public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {

    if (value == null || value.isEmpty()) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("{portalbase.validation.multipartfile.notnull}")
          .addConstraintViolation();
      return !required;
    }

    if (value.getSize() > maxFileSize) {
      context.disableDefaultConstraintViolation();
      context
          .buildConstraintViolationWithTemplate("{portalbase.validation.multipartfile.maxfilesize}")
          .addConstraintViolation();
      return false;
    }

    return fileExtensions.length == 0 || Arrays.stream(fileExtensions)
        .anyMatch(fileExtension -> fileExtension
            .matches(FileHelper.getExtension(value.getOriginalFilename())));
  }
}
