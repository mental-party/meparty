package com.teammental.mehelper.image;

import com.teammental.mecore.enums.FileExtension;
import java.awt.Color;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(Enclosed.class)
public class ImageFactoryTest {

  public static class CreateEmptyImageTest {

    private EmptyImageProperties emptyImageProperties;

    private int dpi = 300;
    private double height = 6.0;
    private double width = 6.0;
    private Color backgroundColor = Color.WHITE;
    private FileExtension fileExtension = FileExtension.JPG;

    @Before
    public void setUp() {

      emptyImageProperties
          = EmptyImageProperties.builder()
          .imageSize(ImageResolution.builder()
              .dpi(dpi)
              .height(height)
              .width(width))
          .backgroundColor(backgroundColor)
          .fileExtension(fileExtension)
          .build();
    }

    @Test
    public void shouldNotReturnNull() throws IOException {

      byte[] image = ImageFactory.createEmptyImage(emptyImageProperties);
      assertNotNull(image);
    }
  }
}
