package com.teammental.mevalidation.constraint.to.multipartfile;

import com.teammental.mecore.enums.FileExtension;
import com.teammental.mevalidation.constraint.MultipartFileConstraint;
import com.teammental.mevalidation.constraint.to.Config;
import org.springframework.web.multipart.MultipartFile;

public class FileTxtTo {

  @MultipartFileConstraint(maxFileSize = Config.MAX_SIZE, fileExtensions = FileExtension.TXT)
  private MultipartFile file;

  public MultipartFile getFile() {
    return file;
  }

  public void setFile(MultipartFile file) {
    this.file = file;
  }
}
