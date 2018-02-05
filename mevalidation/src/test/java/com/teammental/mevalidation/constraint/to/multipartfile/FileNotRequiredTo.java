package com.teammental.mevalidation.constraint.to.multipartfile;

import com.teammental.mevalidation.constraint.MultipartFileConstraint;
import com.teammental.mevalidation.constraint.to.Config;
import org.springframework.web.multipart.MultipartFile;

public class FileNotRequiredTo {

  @MultipartFileConstraint(required = false, maxFileSize = Config.MAX_SIZE)
  private MultipartFile file;

  public MultipartFile getFile() {
    return file;
  }

  public void setFile(MultipartFile file) {
    this.file = file;
  }
}
