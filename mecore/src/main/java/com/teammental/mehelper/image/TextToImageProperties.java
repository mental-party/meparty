package com.teammental.mehelper.image;

import com.teammental.mecore.enums.FileExtension;
import com.teammental.mecore.enums.FileType;
import com.teammental.mecore.enums.FontType;
import com.teammental.mehelper.AssertHelper;
import java.awt.Color;

public class TextToImageProperties {

  private Color backgroundColor;
  private Color fontColor;
  private FontType fontType;
  private int maxFontSize;
  private int maxWidth;
  private int maxHeight;
  private int dpi;
  private FileExtension fileExtension;


  TextToImageProperties(Color backgroundColor,
                        Color fontColor,
                        FontType fontType,
                        int maxFontSize,
                        int maxWidth,
                        int maxHeight,
                        int dpi,
                        FileExtension fileExtension) {

    this.backgroundColor = backgroundColor;

    this.fontColor = fontColor;
    this.fontType = fontType;
    this.maxFontSize = maxFontSize;
    this.maxWidth = maxWidth;
    this.maxHeight = maxHeight;
    this.dpi = dpi;
    this.fileExtension = fileExtension;
  }

  public Color getBackgroundColor() {

    return backgroundColor;
  }

  public Color getFontColor() {

    return fontColor;
  }

  public FontType getFontType() {

    return fontType;
  }

  public int getMaxFontSize() {

    return maxFontSize;
  }

  public int getMaxWidth() {

    return maxWidth;
  }

  public int getMaxHeight() {

    return maxHeight;
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

    Builder fontColor(Color fontColor);

    Builder fontType(FontType fontType);

    Builder maxFontSize(int maxFontSize);

    /**
     * Height and width properties are max value.
     *
     * @param imageResolution image resolution.
     * @return builder.
     */
    Builder imageSize(ImageResolution imageResolution);

    Builder fileExtension(FileExtension fileExtension);

    TextToImageProperties build();
  }

  private static class DefaultBuilderImpl
      implements Builder {

    private Color backgroundColor;
    private Color fontColor;
    private FontType fontType;
    private int maxFontSize;
    private ImageResolution imageResolution;
    private FileExtension fileExtension;

    @Override
    public Builder backgroundColor(Color backgroundColor) {

      AssertHelper.notNull(backgroundColor);
      this.backgroundColor = backgroundColor;
      return this;
    }

    @Override
    public Builder fontColor(Color fontColor) {

      AssertHelper.notNull(fontColor);
      this.fontColor = fontColor;
      return this;
    }

    @Override
    public Builder fontType(FontType fontType) {

      AssertHelper.notNull(fontType);
      this.fontType = fontType;
      return this;
    }

    @Override
    public Builder maxFontSize(int maxFontSize) {

      if (maxFontSize < 1) {
        throw new IllegalArgumentException("maxFontSize cannot be lower than 1");
      }
      this.maxFontSize = maxFontSize;
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
    public TextToImageProperties build() {

      return new TextToImageProperties(backgroundColor,
          fontColor,
          fontType,
          maxFontSize,
          imageResolution.getWidth(),
          imageResolution.getHeight(),
          imageResolution.getDpi(),
          fileExtension);
    }
  }
}
