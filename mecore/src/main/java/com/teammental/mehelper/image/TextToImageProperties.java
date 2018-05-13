package com.teammental.mehelper.image;

import com.teammental.mecore.enums.FileExtension;
import com.teammental.mecore.enums.FileType;
import com.teammental.mecore.enums.FontType;
import com.teammental.mehelper.AssertHelper;
import com.teammental.mehelper.StringHelper;
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
  private String text;


  TextToImageProperties(Color backgroundColor,
                        Color fontColor,
                        FontType fontType,
                        int maxFontSize,
                        int maxWidth,
                        int maxHeight,
                        int dpi,
                        FileExtension fileExtension,
                        String text) {

    this.backgroundColor = backgroundColor;

    this.fontColor = fontColor;
    this.fontType = fontType;
    this.maxFontSize = maxFontSize;
    this.maxWidth = maxWidth;
    this.maxHeight = maxHeight;
    this.dpi = dpi;
    this.fileExtension = fileExtension;
    this.text = text;
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

  public String getText() {

    return text;
  }

  public static Builder getbuilder() {

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

    Builder text(String text);

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
    private String text;

    @Override
    public Builder backgroundColor(Color backgroundColor) {

      this.backgroundColor = backgroundColor;
      return this;
    }

    @Override
    public Builder fontColor(Color fontColor) {

      this.fontColor = fontColor;
      return this;
    }

    @Override
    public Builder fontType(FontType fontType) {

      this.fontType = fontType;
      return this;
    }

    @Override
    public Builder maxFontSize(int maxFontSize) {


      this.maxFontSize = maxFontSize;
      return this;
    }

    @Override
    public Builder imageSize(ImageResolution imageResolution) {

      this.imageResolution = imageResolution;
      return this;
    }

    @Override
    public Builder fileExtension(FileExtension fileExtension) {


      this.fileExtension = fileExtension;
      return this;
    }

    @Override
    public Builder text(String text) {


      this.text = text;
      return this;
    }

    @Override
    public TextToImageProperties build() {

      AssertHelper.notNull(backgroundColor);
      AssertHelper.notNull(fontColor);
      AssertHelper.notNull(fontType);
      if (maxFontSize < 1) {
        throw new IllegalArgumentException("maxFontSize cannot be lower than 1");
      }
      AssertHelper.notNull(imageResolution);
      AssertHelper.notNull(fileExtension);
      if (!fileExtension.getFileType()
          .equals(FileType.IMAGE)) {

        throw new IllegalArgumentException(fileExtension.toString()
            + " is not a valid Image extension.");
      }
      
      if (text != null) {
        text = text.trim();
      }
      if (StringHelper.isNullOrEmpty(text)) {
        throw new IllegalArgumentException("text cannot be null or empty");
      }

      return new TextToImageProperties(backgroundColor,
          fontColor,
          fontType,
          maxFontSize,
          imageResolution.getWidth(),
          imageResolution.getHeight(),
          imageResolution.getDpi(),
          fileExtension,
          text);
    }
  }
}