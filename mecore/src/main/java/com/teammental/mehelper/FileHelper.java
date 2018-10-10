package com.teammental.mehelper;

import com.teammental.mecore.enums.FileExtension;
import java.util.Arrays;
import java.util.Locale;

public class FileHelper {

  /**
   * Extracts extension of the given fileName.
   *
   * @param fileName filename
   * @return returns extension without '.' part, if there is no extension returns empty string
   */
  public static String getExtension(String fileName) {

    char ch;
    int len;
    if (fileName == null
        || (len = fileName.length()) == 0
        || (ch = fileName.charAt(len - 1)) == '/' || ch == '\\' || //in the case of a directory
        ch == '.') { //in the case of . or ..
      return "";
    }

    int dotInd = fileName.lastIndexOf('.');
    int sepInd = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

    if (dotInd <= sepInd) {
      return "";
    } else {
      return fileName.substring(dotInd + 1).toLowerCase(Locale.ENGLISH);
    }
  }

  /**
   * Extracts {@link FileExtension} of the given fileName.
   *
   * @param fileName
   * @return returns file extension, if extension is not known or empty returns {@link
   * FileExtension#UNKNOWN}
   */
  public static FileExtension getFileExtension(String fileName) {

    String ext = getExtension(fileName);

    return Arrays.stream(FileExtension.values())
        .filter(fileExtension -> fileExtension
            .matches(ext))
        .findFirst()
        .orElse(FileExtension.UNKNOWN);
  }
}
