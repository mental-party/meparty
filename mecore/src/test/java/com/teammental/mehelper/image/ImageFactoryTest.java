package com.teammental.mehelper.image;

import com.teammental.mecore.enums.FileExtension;
import com.teammental.mecore.enums.ImageColorType;
import java.awt.Color;
import java.io.IOException;
import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Enclosed.class)
public class ImageFactoryTest {

  public static class CreateEmptyImageTest {

    private EmptyImageProperties emptyImageProperties;

    @Before
    public void setUp() {

      int dpi = 300;
      double height = 6.0;
      double width = 6.0;
      Color backgroundColor = Color.WHITE;
      FileExtension fileExtension = FileExtension.JPG;

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

    @Test
    public void shouldSetDpiAsGiven() throws IOException, ImageReadException {

      byte[] image = ImageFactory.createEmptyImage(emptyImageProperties);

      ImageInfo imageInfo = Sanselan.getImageInfo(image);

      final int physicalWidthDpi = imageInfo.getPhysicalWidthDpi();
      final int physicalHeightDpi = imageInfo.getPhysicalHeightDpi();

      assertEquals(emptyImageProperties.getDpi(), physicalHeightDpi);
      assertEquals(emptyImageProperties.getDpi(), physicalWidthDpi);
    }

    @Test
    public void shouldSetColorFormatAsRgb_whenFileExtensionJpg()
        throws IOException, ImageReadException {

      byte[] image = ImageFactory.createEmptyImage(emptyImageProperties);

      ImageInfo imageInfo = Sanselan.getImageInfo(image);

      final int colorType = imageInfo.getColorType();

      assertEquals(ImageColorType.RGB.getValue(), colorType);
    }

    @Test
    public void shouldSetWidthAsGiven()
        throws IOException, ImageReadException {

      byte[] image = ImageFactory.createEmptyImage(emptyImageProperties);

      ImageInfo imageInfo = Sanselan.getImageInfo(image);

      final int width = imageInfo.getWidth();

      assertEquals(emptyImageProperties.getWidth(), width);
    }

    @Test
    public void shouldSetHeightAsGiven()
        throws IOException, ImageReadException {

      byte[] image = ImageFactory.createEmptyImage(emptyImageProperties);

      ImageInfo imageInfo = Sanselan.getImageInfo(image);

      final int height = imageInfo.getHeight();

      assertEquals(emptyImageProperties.getHeight(), height);
    }
  }
}
