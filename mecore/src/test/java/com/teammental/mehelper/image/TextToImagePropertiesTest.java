package com.teammental.mehelper.image;

import com.teammental.mecore.enums.FileExtension;
import com.teammental.mecore.enums.FontType;
import java.awt.Color;
import org.junit.Test;

import static org.junit.Assert.*;

public class TextToImagePropertiesTest {

  private Color backgroundColor = Color.WHITE;
  private Color fontColor = Color.BLACK;
  private int dpi = 300;
  private int maxFontSize = 100;
  private int minFontSize = 10;
  private int height = 10;
  private int width = 10;
  private FileExtension fileExtension = FileExtension.JPG;
  private FontType fontType = FontType.ARIAL;
  private String text = "Text";

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowException_whenBackgroundColorIsNull() {

    final Color colorNull = null;

    TextToImageProperties properties =
        TextToImageProperties.getbuilder()
            .backgroundColor(colorNull)
            .fileExtension(fileExtension)
            .fontColor(fontColor)
            .fontType(fontType)
            .imageSize(ImageResolution.getBuilder()
                .dpi(dpi)
                .heightPx(height)
                .widthPx(width))
            .maxFontSize(maxFontSize)
            .minFontSize(minFontSize)
            .text(text)
            .build();

  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowException_whenFontColorIsNull() {

    final Color colorNull = null;

    TextToImageProperties properties =
        TextToImageProperties.getbuilder()
            .backgroundColor(backgroundColor)
            .fileExtension(fileExtension)
            .fontColor(colorNull)
            .fontType(fontType)
            .imageSize(ImageResolution.getBuilder()
                .dpi(dpi)
                .heightPx(height)
                .widthPx(width))
            .maxFontSize(maxFontSize)
            .minFontSize(minFontSize)
            .text(text)
            .build();

  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowException_whenFontTypeIsNull() {

    final FontType fontTypeNull = null;

    TextToImageProperties properties =
        TextToImageProperties.getbuilder()
            .backgroundColor(backgroundColor)
            .fileExtension(fileExtension)
            .fontColor(fontColor)
            .fontType(fontTypeNull)
            .imageSize(ImageResolution.getBuilder()
                .dpi(dpi)
                .heightPx(height)
                .widthPx(width))
            .maxFontSize(maxFontSize)
            .minFontSize(minFontSize)
            .text(text)
            .build();

  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowException_whenMaxFontSizeIsLowerThanOne() {

    final int maxFontSizeZero = 0;

    TextToImageProperties properties =
        TextToImageProperties.getbuilder()
            .backgroundColor(backgroundColor)
            .fileExtension(fileExtension)
            .fontColor(fontColor)
            .fontType(fontType)
            .imageSize(ImageResolution.getBuilder()
                .dpi(dpi)
                .heightPx(height)
                .widthPx(width))
            .maxFontSize(maxFontSizeZero)
            .minFontSize(minFontSize)
            .text(text)
            .build();

  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowException_whenMinFontSizeIsLowerThanOne() {

    final int minFontSizeZero = 0;

    TextToImageProperties properties =
        TextToImageProperties.getbuilder()
            .backgroundColor(backgroundColor)
            .fileExtension(fileExtension)
            .fontColor(fontColor)
            .fontType(fontType)
            .imageSize(ImageResolution.getBuilder()
                .dpi(dpi)
                .heightPx(height)
                .widthPx(width))
            .maxFontSize(maxFontSize)
            .minFontSize(minFontSizeZero)
            .text(text)
            .build();

  }
}