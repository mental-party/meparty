package com.teammental.mehelper;

public class ResolutionHelper {

  // The number of CMs per Inch
  public static final double CM_PER_INCH = 0.393700787d;
  // The number of Inches per CMs
  public static final double INCH_PER_CM = 2.545d;
  // The number of Inches per mm's
  public static final double INCH_PER_MM = 25.45d;

  /**
   * Converts the given pixels to cm's based on the supplied DPI.
   *
   * @param pixels pixel value
   * @param dpi    DPI value
   * @return CM value
   */
  public static double pixelsToCms(double pixels, double dpi) {

    return inchesToCms(pixels / dpi);
  }

  /**
   * Converts the given cm's to pixels based on the supplied DPI.
   *
   * @param cms CM value
   * @param dpi DPI value
   * @return pixel value
   */
  public static double cmsToPixel(double cms, double dpi) {

    return cmToInches(cms) * dpi;
  }

  /**
   * Converts the given cm's to inches.
   *
   * @param cms cm value
   * @return inch value
   */
  public static double cmToInches(double cms) {

    return cms * CM_PER_INCH;
  }

  /**
   * Converts the given inches to cm's.
   *
   * @param inch inch value
   * @return CM value
   */
  public static double inchesToCms(double inch) {

    return inch * INCH_PER_CM;
  }
}
