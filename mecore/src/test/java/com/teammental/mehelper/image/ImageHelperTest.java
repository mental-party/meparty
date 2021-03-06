package com.teammental.mehelper.image;

import com.teammental.mecore.enums.FileExtension;
import com.teammental.mecore.enums.ImageColorType;
import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
public class ImageHelperTest {

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
          = EmptyImageProperties.getBuilder()
          .imageSize(ImageResolution.getBuilder()
              .dpi(dpi)
              .heightCm(height)
              .widthCm(width))
          .backgroundColor(backgroundColor)
          .fileExtension(fileExtension)
          .build();
    }

    @Test
    public void shouldNotReturnNull() throws IOException {

      byte[] image = ImageHelper.createEmptyImage(emptyImageProperties);
      assertNotNull(image);
    }

    @Test
    public void shouldSetDpiAsGiven() throws IOException, ImageReadException {

      byte[] image = ImageHelper.createEmptyImage(emptyImageProperties);

      ImageInfo imageInfo = Sanselan.getImageInfo(image);

      final int physicalWidthDpi = imageInfo.getPhysicalWidthDpi();
      final int physicalHeightDpi = imageInfo.getPhysicalHeightDpi();

      assertEquals(emptyImageProperties.getDpi(), physicalHeightDpi);
      assertEquals(emptyImageProperties.getDpi(), physicalWidthDpi);
    }

    @Test
    public void shouldSetColorFormatAsRgb_whenFileExtensionJpg()
        throws IOException, ImageReadException {

      byte[] image = ImageHelper.createEmptyImage(emptyImageProperties);

      ImageInfo imageInfo = Sanselan.getImageInfo(image);

      final int colorType = imageInfo.getColorType();

      assertEquals(ImageColorType.RGB.getValue(), colorType);
    }

    @Test
    public void shouldSetWidthAsGiven()
        throws IOException, ImageReadException {

      byte[] image = ImageHelper.createEmptyImage(emptyImageProperties);

      ImageInfo imageInfo = Sanselan.getImageInfo(image);

      final int width = imageInfo.getWidth();

      assertEquals(emptyImageProperties.getWidth(), width);
    }

    @Test
    public void shouldSetHeightAsGiven()
        throws IOException, ImageReadException {

      byte[] image = ImageHelper.createEmptyImage(emptyImageProperties);

      ImageInfo imageInfo = Sanselan.getImageInfo(image);

      final int height = imageInfo.getHeight();

      assertEquals(emptyImageProperties.getHeight(), height);
    }
  }

  public static class ChangeFormatTest {

    private final static Path ORIGINAL_IMAGE =
        Paths.get("src", "test", "resources", "images", "image.jpg");

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_whenOriginalImageDataIsNull() throws IOException {

      final byte[] originalData = null;

      ImageHelper.changeFormat(originalData, FileExtension.JPG);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_whenFileExtensionIsNull() throws IOException {

      final FileExtension fileExtension = null;

      ImageHelper.changeFormat(new byte[0], fileExtension);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_whenOriginalDataIsNotImage() throws IOException {

      ImageHelper.changeFormat(new byte[0], FileExtension.JPG);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_whenFileExtensionIsNotImage() throws IOException {

      byte[] data = Files.readAllBytes(ORIGINAL_IMAGE);
      ImageHelper.changeFormat(data, FileExtension.PDF);
    }
  }

}
