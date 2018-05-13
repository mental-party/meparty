package com.teammental.mehelper.image;

import com.teammental.mehelper.ResolutionHelper;

public class ImageSize {

  private int width;
  private int height;

  ImageSize(int width, int height) {

    this.width = width;
    this.height = height;
  }

  public int getWidth() {

    return width;
  }

  public void setWidth(int width) {

    this.width = width;
  }

  public int getHeight() {

    return height;
  }

  public void setHeight(int height) {

    this.height = height;
  }

  public static HeightBuilder builder() {

    return new BuilderImpl();
  }

  interface HeightBuilder {

    WidthBuilder height(int height);

    WidthBuilder height(double cm, int dpi);
  }

  interface WidthBuilder {

    ImageSize width(int width);

    ImageSize width(double cm, int dpi);
  }

  private static class BuilderImpl
      implements HeightBuilder, WidthBuilder {

    private int height;
    private int width;

    @Override
    public WidthBuilder height(int height) {

      if (height < 1) {
        throw new IllegalArgumentException("height cannot be lower than 0");
      }
      this.height = height;
      return this;
    }

    @Override
    public WidthBuilder height(double cm, int dpi) {

      if (cm <= 0) {
        throw new IllegalArgumentException("cm cannot be lower than or equal to 0");
      }
      if (dpi < 0) {
        throw new IllegalArgumentException("dpi cannot be lower than 0");
      }
      this.height = (int) Math.round(ResolutionHelper.cmsToPixel(cm, dpi));
      return this;
    }

    @Override
    public ImageSize width(int width) {

      if (width < 1) {
        throw new IllegalArgumentException("width cannot be lower than 0");
      }
      this.width = width;
      return new ImageSize(this.width, this.height);
    }

    @Override
    public ImageSize width(double cm, int dpi) {

      if (cm <= 0) {
        throw new IllegalArgumentException("cm cannot be lower than or equal to 0");
      }
      if (dpi < 0) {
        throw new IllegalArgumentException("dpi cannot be lower than 0");
      }
      this.width = (int) Math.round(ResolutionHelper.cmsToPixel(cm, dpi));
      return new ImageSize(this.width, this.height);
    }
  }
}
