package com.teammental.mehelper.image;

import org.junit.Test;

import static org.junit.Assert.*;

public class ImageResolutionTest {

  private int dpi = 10;
  private int heightPx = 10;
  private int widthPx = 10;
  private double heightCm = 10.0;
  private double widthCm = 10.0;

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowException_whenDpiIsLowerThanOne() {

    final int dpiZero = 0;

    ImageResolution imageResolution
        = ImageResolution.getBuilder()
        .dpi(dpiZero)
        .heightCm(heightCm)
        .widthCm(widthCm);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowException_whenHeightCmIsLowerThanZero() {

    final double heightCmLowerZero = -1;

    ImageResolution imageResolution
        = ImageResolution.getBuilder()
        .dpi(dpi)
        .heightCm(heightCmLowerZero)
        .widthCm(widthCm);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowException_whenHeightCmEqualsToZero() {

    final double heightCmZero = 0;

    ImageResolution imageResolution
        = ImageResolution.getBuilder()
        .dpi(dpi)
        .heightCm(heightCmZero)
        .widthCm(widthCm);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowException_whenWidthCmIsLowerThanZero() {

    final double widthCmLowerZero = -1;

    ImageResolution imageResolution
        = ImageResolution.getBuilder()
        .dpi(dpi)
        .heightCm(heightCm)
        .widthCm(widthCmLowerZero);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowException_whenWidthtCmEqualsToZero() {

    final double widthCmZero = 0;

    ImageResolution imageResolution
        = ImageResolution.getBuilder()
        .dpi(dpi)
        .heightCm(heightCm)
        .widthCm(widthCmZero);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowException_whenHeightPxIsLowerThanOne() {

    final int heightPxZero = 0;

    ImageResolution imageResolution
        = ImageResolution.getBuilder()
        .dpi(dpi)
        .heightPx(heightPxZero)
        .widthPx(widthPx);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowException_whenWidthPxIsLowerThanOne() {

    final int widthPxZero = 0;

    ImageResolution imageResolution
        = ImageResolution.getBuilder()
        .dpi(dpi)
        .heightPx(heightPx)
        .widthPx(widthPxZero);
  }
}