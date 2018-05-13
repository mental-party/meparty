package com.teammental.mecore.enums;

import java.util.Arrays;
import java.util.Locale;

public enum FileExtension {
  JPG(FileType.IMAGE, "jpg", "jpeg"),
  PNG(FileType.IMAGE, "png"),
  GIF(FileType.IMAGE, "gif"),
  BMP(FileType.IMAGE, "bmp"),
  MP4(FileType.VIDEO, "mp4"),
  MP3(FileType.AUDIO, "mp3"),
  XLS(FileType.DOCUMENT, "xls"),
  XLSX(FileType.DOCUMENT, "xlsx"),
  DOC(FileType.DOCUMENT, "doc"),
  DOCX(FileType.DOCUMENT, "docx"),
  TXT(FileType.TEXT, "txt"),
  PDF(FileType.DOCUMENT, "pdf"),
  RTF(FileType.DOCUMENT, "rtf"),
  ZIP(FileType.COMPRESSED, "zip"),
  RAR(FileType.COMPRESSED, "rar");

  private String[] extensions;
  private FileType fileType;

  FileExtension(FileType fileType, String... extensions) {

    this.fileType = fileType;
    this.extensions = extensions;
  }

  public String[] getExtensions() {

    return extensions.clone();
  }

  public FileType getFileType() {

    return fileType;
  }

  public boolean matches(String extension) {

    return extension != null && Arrays.stream(extensions)
        .anyMatch(s -> s.equals(extension.toLowerCase(Locale.ENGLISH)));
  }
}
