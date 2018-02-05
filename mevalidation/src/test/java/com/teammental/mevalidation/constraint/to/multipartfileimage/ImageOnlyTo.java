package com.teammental.mevalidation.constraint.to.multipartfileimage;

import com.teammental.mevalidation.constraint.MultipartFileImageConstraint;
import org.springframework.web.multipart.MultipartFile;

public class ImageOnlyTo {

  @MultipartFileImageConstraint
  private MultipartFile multipartFile;


  public MultipartFile getMultipartFile() {
    return multipartFile;
  }

  public void setMultipartFile(MultipartFile file) {
    this.multipartFile = file;
  }
}
