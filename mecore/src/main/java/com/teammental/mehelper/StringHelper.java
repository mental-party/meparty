package com.teammental.mehelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by erhan.karakaya on 8/25/2017.
 */
public class StringHelper {
  /**
   * Makes the first letter uppercase of the given String.
   *
   * @param str will be used in capitaling operation.
   * @return capitalized state of the given param.
   */
  public static String capitalizeFirstLetter(final String str) {
    if (isNullOrEmpty(str)) {
      return str;
    }
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(Character.toTitleCase(str.charAt(0)));
    if (str.length() > 1) {
      stringBuilder.append(str.substring(1));
    }
    return stringBuilder.toString();
  }

  /**
   * Generates random string.
   *
   * @param length desired length of string.
   * @return randomly generated string.
   */
  public static String generateRandomString(final int length) {
    char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    StringBuilder sb = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < length; i++) {
      char c = chars[random.nextInt(chars.length)];
      sb.append(c);
    }
    String output = sb.toString();
    return output;
  }

  /**
   * Checks if a String object is null or empty.
   *
   * @param str String object which will be checked
   * @return true if str is empty or null, false if not
   */
  public static boolean isNullOrEmpty(final String str) {
    return str == null || str.length() == 0;
  }


  /**
   * Splits a String object by capital letters in it.
   *
   * @param str String object which will be splitted
   * @return List of String
   */
  public static List<String> splitByCapitalLetters(final String str) {

    return splitByCapitalLetters(str, false);
  }

  /**
   * Splits a String object by capital letters in it.
   *
   * @param str                    String object which will be splitted
   * @param onlyFromFirstOccurance if true, str will be splitted into two sub-strings
   *                               by first capital character
   * @return List of String
   */
  public static List<String> splitByCapitalLetters(final String str,
                                                   final boolean onlyFromFirstOccurance) {
    if (isNullOrEmpty(str)) {
      return null;
    }

    char[] chars = str.toCharArray();
    List<String> split = new ArrayList<>();

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(chars[0]);
    for (int i = 1; i < chars.length; i++) {
      char ch = chars[i];
      if (Character.isUpperCase(ch)) {
        split.add(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        if (onlyFromFirstOccurance) {
          for (int j = i; j < chars.length; j++) {
            stringBuilder.append(chars[j]);
          }
          break;
        }
      }
      stringBuilder.append(ch);
    }
    split.add(stringBuilder.toString());

    return split;
  }

  /**
   * Returns first element in an array unless it has a value;
   * otherwise returns an empty String.
   * @param strings String array
   * @return first or an empty String
   */
  public static String getFirstOrEmpty(String[] strings) {

    if (strings == null || strings.length == 0 || strings[0] == null) {
      return "";
    }

    return strings[0];
  }
}
