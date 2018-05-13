package com.teammental.mehelper.image;

import com.teammental.mehelper.ResolutionHelper;

public class ImageResolution {

  private int width;
  private int height;
  private int dpi;

  private ImageResolution(int width, int height, int dpi) {

    this.width = width;
    this.height = height;
    this.dpi = dpi;
  }

  public int getWidth() {

    return width;
  }

  public int getHeight() {

    return height;
  }

  public int getDpi() {

    return dpi;
  }

  public static DpiBuilder getBuilder() {

    return new BuilderImpl();
  }

  public interface HeightBuilder {

    WidthBuilder heightPx(int height);

    WidthBuilder heightCm(double cm);
  }

  public interface WidthBuilder {

    ImageResolution widthPx(int width);

    ImageResolution widthCm(double cm);
  }

  public interface DpiBuilder {

    HeightBuilder dpi(int dpi);
  }

  private static class BuilderImpl
      implements HeightBuilder, WidthBuilder, DpiBuilder {

    private int height;
    private int width;
    private int dpi;

    @Override
    public WidthBuilder heightPx(int height) {

      if (height < 1) {
        throw new IllegalArgumentException("height cannot be lower than 0");
      }
      this.height = height;
      return this;
    }

    @Override
    public WidthBuilder heightCm(double cm) {

      if (cm <= 0) {
        throw new IllegalArgumentException("cm cannot be lower than or equal to 0");
      }
      this.height = (int) Math.round(ResolutionHelper.cmsToPixel(cm, dpi));
      return this;
    }

    @Override
    public ImageResolution widthPx(int width) {

      if (width < 1) {
        throw new IllegalArgumentException("width cannot be lower than 0");
      }
      this.width = width;
      return new ImageResolution(this.width, this.height, this.dpi);
    }

    @Override
    public ImageResolution widthCm(double cm) {

      if (cm <= 0) {
        throw new IllegalArgumentException("cm cannot be lower than or equal to 0");
      }

      this.width = (int) Math.round(ResolutionHelper.cmsToPixel(cm, dpi));
      return new ImageResolution(this.width, this.height, this.dpi);
    }

    @Override
    public HeightBuilder dpi(int dpi) {

      if (dpi < 0) {
        throw new IllegalArgumentException("dpi cannot be lower than 0");
      }
      this.dpi = dpi;
      return this;
    }
  }
}
