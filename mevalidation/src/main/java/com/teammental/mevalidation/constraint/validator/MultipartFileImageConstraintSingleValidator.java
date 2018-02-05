package com.teammental.mevalidation.constraint.validator;

import com.teammental.mecore.enums.ImageColorType;
import com.teammental.mevalidation.constraint.MultipartFileImageConstraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.Sanselan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class MultipartFileImageConstraintSingleValidator
    implements ConstraintValidator<MultipartFileImageConstraint, MultipartFile> {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(MultipartFileImageConstraintSingleValidator.class);

  private int dpi;
  private ImageColorType colorType;
  private int maxHeight;
  private int minHeight;
  private int maxWidth;
  private int minWidth;

  @Override
  public void initialize(MultipartFileImageConstraint constraintAnnotation) {
    this.dpi = constraintAnnotation.dpi();
    this.colorType = constraintAnnotation.colorType();
    this.maxHeight = constraintAnnotation.maxHeight();
    this.minHeight = constraintAnnotation.minHeight();
    this.maxWidth = constraintAnnotation.maxWidth();
    this.minWidth = constraintAnnotation.minWidth();
  }

  @Override
  public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {

    if (value == null || value.isEmpty()) {

      return true;
    }

    ImageInfo imageInfo;
    try {
      imageInfo = Sanselan.getImageInfo(value.getBytes());

      if (imageInfo == null) {
        return false;
      }
    } catch (Exception ex) {
      LOGGER.error(ex.getLocalizedMessage());
      return false;
    }

    try {

      checkDpiAndColorType(imageInfo, context);

    } catch (Exception ex) {
      LOGGER.error(ex.getLocalizedMessage());
      return false;
    }


    try {

      checkWidth(imageInfo, context);

    } catch (Exception ex) {
      LOGGER.error(ex.getLocalizedMessage());
      return false;
    }


    try {

      checkHeight(imageInfo, context);

    } catch (Exception ex) {
      LOGGER.error(ex.getLocalizedMessage());
      return false;
    }


    return true;
  }

  private void checkDpiAndColorType(ImageInfo imageInfo, ConstraintValidatorContext context)
      throws Exception {

    final int physicalWidthDpi = imageInfo.getPhysicalWidthDpi();
    final int physicalHeightDpi = imageInfo.getPhysicalHeightDpi();

    if (dpi > 0 && (physicalHeightDpi != dpi || physicalWidthDpi != dpi)) {

      disableDefaultViolationAndAddNew(context,
          "portalbase.validation.multipartimagefile.dpi");

      throw new Exception();
    }

    if (colorType.getValue() > -1 && imageInfo.getColorType() != colorType.getValue()) {

      disableDefaultViolationAndAddNew(context,
          "portalbase.validation.multipartimagefile.colortype");

      throw new Exception();
    }
  }

  private void checkWidth(ImageInfo imageInfo, ConstraintValidatorContext context)
      throws Exception {

    if (maxWidth > 0 && imageInfo.getWidth() > maxWidth) {

      disableDefaultViolationAndAddNew(context,
          "portalbase.validation.multipartimagefile.maxWidth");

      throw new Exception();
    }

    if (minWidth > 0 && imageInfo.getWidth() < minWidth) {

      disableDefaultViolationAndAddNew(context,
          "portalbase.validation.multipartimagefile.minWidth");

      throw new Exception();
    }
  }

  private void checkHeight(ImageInfo imageInfo, ConstraintValidatorContext context)
      throws Exception {

    if (maxHeight > 0 && imageInfo.getHeight() > maxHeight) {

      disableDefaultViolationAndAddNew(context,
          "portalbase.validation.multipartimagefile.maxHeight");

      throw new Exception();
    }


    if (minHeight > 0 && imageInfo.getHeight() < minHeight) {

      disableDefaultViolationAndAddNew(context,
          "portalbase.validation.multipartimagefile.minHeight");

      throw new Exception();
    }

  }


  private void disableDefaultViolationAndAddNew(ConstraintValidatorContext context,
                                                String messageTemplate) {
    context.disableDefaultConstraintViolation();
    context
        .buildConstraintViolationWithTemplate("{" + messageTemplate + "}")
        .addConstraintViolation();
  }
}
