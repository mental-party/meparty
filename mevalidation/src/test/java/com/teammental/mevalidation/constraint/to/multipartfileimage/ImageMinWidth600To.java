package com.teammental.mevalidation.constraint.to.multipartfileimage;

import com.teammental.mevalidation.constraint.MultipartFileImageConstraint;
import org.springframework.web.multipart.MultipartFile;

public class ImageMinWidth600To {

  @MultipartFileImageConstraint(minWidth = 600)
  private MultipartFile multipartFile;

  public MultipartFile getMultipartFile() {
    return multipartFile;
  }

  public void setMultipartFile(MultipartFile multipartFile) {
    this.multipartFile = multipartFile;
  }
}
