package com.teammental.mehelper.image;

import com.teammental.mecore.enums.FileExtension;
import java.awt.Color;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmptyImagePropertiesTest {

  private Color backgroundColor = Color.WHITE;
  private int dpi = 300;
  private int height = 10;
  private int width = 10;
  private FileExtension fileExtension = FileExtension.JPG;

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowException_whenBackgroundColorIsNull() {

    final Color colorNull = null;

    EmptyImageProperties properties =
        EmptyImageProperties.getBuilder()
            .backgroundColor(colorNull)
            .fileExtension(fileExtension)
            .imageSize(ImageResolution.getBuilder()
                .dpi(dpi)
                .heightPx(height)
                .widthPx(width))
            .build();

  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowException_whenImageResolutionIsNull() {

    final ImageResolution imageResolutionNull = null;

    EmptyImageProperties properties =
        EmptyImageProperties.getBuilder()
            .backgroundColor(backgroundColor)
            .fileExtension(fileExtension)
            .imageSize(imageResolutionNull)
            .build();

  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowException_whenFileExtensionIsNull() {

    final FileExtension fileExtensionNull = null;

    EmptyImageProperties properties =
        EmptyImageProperties.getBuilder()
            .backgroundColor(backgroundColor)
            .fileExtension(fileExtensionNull)
            .imageSize(ImageResolution
                .getBuilder()
                .dpi(dpi)
                .heightCm(height)
                .widthCm(width))
            .build();

  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowException_whenFileExtensionIsNotImage() {

    final FileExtension fileExtensionNotImage = FileExtension.DOC;

    EmptyImageProperties properties =
        EmptyImageProperties.getBuilder()
            .backgroundColor(backgroundColor)
            .fileExtension(fileExtensionNotImage)
            .imageSize(ImageResolution
                .getBuilder()
                .dpi(dpi)
                .heightCm(height)
                .widthCm(width))
            .build();

  }
}