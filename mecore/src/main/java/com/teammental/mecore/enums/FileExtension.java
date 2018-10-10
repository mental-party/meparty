package com.teammental.mecore.enums;

import java.util.Arrays;
import java.util.Locale;

public enum FileExtension {
  NONE(FileType.UNKNOWN, 1, "", "unknown"),
  JPG(FileType.IMAGE, 2, "jpg", "jpeg"),
  PNG(FileType.IMAGE, 3, "png"),
  GIF(FileType.IMAGE, 4, "gif"),
  BMP(FileType.IMAGE, 5, "bmp"),
  MP4(FileType.VIDEO, 6, "mp4"),
  MP3(FileType.AUDIO, 7, "mp3"),
  XLS(FileType.DOCUMENT, 8, "xls"),
  XLSX(FileType.DOCUMENT, 9, "xlsx"),
  DOC(FileType.DOCUMENT, 10, "doc"),
  DOCX(FileType.DOCUMENT, 11, "docx"),
  TXT(FileType.TEXT, 12, "txt"),
  PDF(FileType.DOCUMENT, 13, "pdf"),
  RTF(FileType.DOCUMENT, 14, "rtf"),
  ZIP(FileType.COMPRESSED, 15, "zip"),
  RAR(FileType.COMPRESSED, 16, "rar");

  private String[] extensions;
  private FileType fileType;
  private int extensionId;

  FileExtension(FileType fileType, int extensionId, String... extensions) {

    this.fileType = fileType;
    this.extensions = extensions;
    this.extensionId = extensionId;
  }

  public String[] getExtensions() {

    return extensions.clone();
  }

  public FileType getFileType() {

    return fileType;
  }

  public int getExtensionId() {

    return extensionId;
  }

  /**
   * Checks if given extension is matching.
   *
   * @param extension extension
   * @return true if matches
   */
  public boolean matches(String extension) {

    return extension != null && Arrays.stream(extensions)
        .anyMatch(s -> s.equals(extension.toLowerCase(Locale.ENGLISH)));
  }
}
