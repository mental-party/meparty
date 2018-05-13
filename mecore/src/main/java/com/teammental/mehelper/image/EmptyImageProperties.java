package com.teammental.mehelper.image;

import com.teammental.mecore.enums.FileExtension;
import com.teammental.mecore.enums.FileType;
import com.teammental.mehelper.AssertHelper;
import java.awt.Color;

public class EmptyImageProperties {

  private Color backgroundColor;
  private int width;
  private int height;
  private int dpi;
  private FileExtension fileExtension;

  EmptyImageProperties(Color backgroundColor,
                       int width,
                       int height,
                       int dpi,
                       FileExtension fileExtension) {

    this.backgroundColor = backgroundColor;
    this.width = width;
    this.height = height;
    this.dpi = dpi;
    this.fileExtension = fileExtension;
  }

  public Color getBackgroundColor() {

    return backgroundColor;
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

  public FileExtension getFileExtension() {

    return fileExtension;
  }

  public static Builder builder() {

    return new DefaultBuilderImpl();
  }


  public interface Builder {

    Builder backgroundColor(Color backgroundColor);

    Builder imageSize(ImageResolution imageResolution);

    Builder fileExtension(FileExtension fileExtension);

    EmptyImageProperties build();
  }

  private static class DefaultBuilderImpl
      implements Builder {

    private Color backgroundColor;
    private ImageResolution imageResolution;
    private FileExtension fileExtension;

    @Override
    public Builder backgroundColor(Color backgroundColor) {

      AssertHelper.notNull(backgroundColor);
      this.backgroundColor = backgroundColor;
      return this;
    }

    @Override
    public Builder imageSize(ImageResolution imageResolution) {

      AssertHelper.notNull(imageResolution);
      this.imageResolution = imageResolution;
      return this;
    }

    @Override
    public Builder fileExtension(FileExtension fileExtension) {

      AssertHelper.notNull(fileExtension);
      if (!fileExtension.getFileType()
          .equals(FileType.IMAGE)) {

        throw new IllegalArgumentException(fileExtension.toString()
            + " is not a valid Image extension.");
      }
      this.fileExtension = fileExtension;
      return this;
    }

    @Override
    public EmptyImageProperties build() {

      return new EmptyImageProperties(backgroundColor,
          imageResolution.getWidth(),
          imageResolution.getHeight(),
          imageResolution.getDpi(),
          fileExtension);
    }
  }
}
