package com.teammental.mevalidation.constraint.to.multipartfileimage;

import com.teammental.mecore.enums.ImageColorType;
import com.teammental.mevalidation.constraint.MultipartFileImageConstraint;
import org.springframework.web.multipart.MultipartFile;

public class ImageRejectRgbTo {

  @MultipartFileImageConstraint(colorType = ImageColorType.BLACK_WHITE)
  private MultipartFile multipartFile;

  public MultipartFile getMultipartFile() {
    return multipartFile;
  }

  public void setMultipartFile(MultipartFile multipartFile) {
    this.multipartFile = multipartFile;
  }
}
