package com.teammental.mevalidation.constraint.to.multipartfile;

import com.teammental.mevalidation.constraint.MultipartFileConstraint;
import com.teammental.mevalidation.constraint.to.Config;
import org.springframework.web.multipart.MultipartFile;

public class FileRequiredTo {

  @MultipartFileConstraint(maxFileSize = Config.MAX_SIZE, required = true)
  private MultipartFile multipartFile;

  public MultipartFile getMultipartFile() {
    return multipartFile;
  }

  public void setMultipartFile(MultipartFile multipartFile) {
    this.multipartFile = multipartFile;
  }
}
