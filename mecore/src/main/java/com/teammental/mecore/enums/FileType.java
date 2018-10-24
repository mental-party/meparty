package com.teammental.mecore.enums;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Collectors;

public enum FileType {

  IMAGE,
  VIDEO,
  AUDIO,
  DOCUMENT,
  COMPRESSED,
  TEXT,
  UNKNOWN,
  SPREADSHEET,
  DATA,
  LOG,
  PRESENTATION;


  /**
   * Gets extensions of this FileType.
   *
   * @return EnumSet of FileExtensions
   */
  public EnumSet<FileExtension> getFileExtensions() {
    return EnumSet.copyOf(Arrays.stream(FileExtension.values())
        .filter(fileExtension -> fileExtension.getFileType().equals(this))
        .collect(Collectors.toSet()));
  }

}
