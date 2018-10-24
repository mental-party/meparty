package com.teammental.mecore.enums;

import java.util.Arrays;
import java.util.Locale;

public enum FileExtension {
  UNKNOWN(FileType.UNKNOWN, 1, "", "unknown"),
  JPG(FileType.IMAGE, 2, "jpg", "jpeg"),
  PNG(FileType.IMAGE, 3, "png"),
  GIF(FileType.IMAGE, 4, "gif"),
  BMP(FileType.IMAGE, 5, "bmp"),
  MP4(FileType.VIDEO, 6, "mp4"),
  MP3(FileType.AUDIO, 7, "mp3"),
  XLS(FileType.SPREADSHEET, 8, "xls"),
  XLSX(FileType.SPREADSHEET, 9, "xlsx"),
  DOC(FileType.DOCUMENT, 10, "doc"),
  DOCX(FileType.DOCUMENT, 11, "docx"),
  TXT(FileType.TEXT, 12, "txt"),
  PDF(FileType.DOCUMENT, 13, "pdf"),
  RTF(FileType.DOCUMENT, 14, "rtf"),
  ZIP(FileType.COMPRESSED, 15, "zip"),
  RAR(FileType.COMPRESSED, 16, "rar"),
  XLR(FileType.SPREADSHEET, 17, "xlr"),
  CSV(FileType.DATA, 18, "csv"),
  XML(FileType.DATA, 19, "xml"),
  MDB(FileType.DATA, 20, "mdb"),
  LOG(FileType.LOG, 21, "log"),
  PPT(FileType.PRESENTATION, 22, "ppt"),
  PPTX(FileType.PRESENTATION, 23, "pptx"),
  PPS(FileType.PRESENTATION, 24, "pps"),
  ODP(FileType.PRESENTATION, 25, "odp"),
  ODS(FileType.SPREADSHEET, 26, "ods"),
  AVI(FileType.VIDEO, 27, "avi"),
  FLV(FileType.VIDEO, 28, "flv"),
  WMV(FileType.VIDEO, 29, "wmv"),
  MKV(FileType.VIDEO, 30, "mkv"),
  MPG(FileType.VIDEO, 31, "mpg"),
  ODT(FileType.DOCUMENT, 32, "odt"),
  _7Z(FileType.COMPRESSED, 33, "7z");

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
