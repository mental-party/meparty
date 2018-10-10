package com.teammental.mecore.enums;

import java.util.Arrays;
import java.util.Locale;

public enum FileExtension {
  JPG(FileType.IMAGE, 1, "jpg", "jpeg"),
  PNG(FileType.IMAGE, 2, "png"),
  GIF(FileType.IMAGE, 3, "gif"),
  BMP(FileType.IMAGE, 4, "bmp"),
  MP4(FileType.VIDEO, 5, "mp4"),
  MP3(FileType.AUDIO, 6, "mp3"),
  XLS(FileType.DOCUMENT, 7, "xls"),
  XLSX(FileType.DOCUMENT, 8, "xlsx"),
  DOC(FileType.DOCUMENT, 8, "doc"),
  DOCX(FileType.DOCUMENT, 10, "docx"),
  TXT(FileType.TEXT, 11, "txt"),
  PDF(FileType.DOCUMENT, 12, "pdf"),
  RTF(FileType.DOCUMENT, 13, "rtf"),
  ZIP(FileType.COMPRESSED, 14, "zip"),
  RAR(FileType.COMPRESSED, 15, "rar");

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
