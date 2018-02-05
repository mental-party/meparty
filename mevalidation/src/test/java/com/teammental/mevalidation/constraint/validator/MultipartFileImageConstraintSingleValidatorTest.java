package com.teammental.mevalidation.constraint.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import com.teammental.mevalidation.constraint.to.multipartfileimage.Image300DpiTo;
import com.teammental.mevalidation.constraint.to.multipartfileimage.Image72dpiTo;
import com.teammental.mevalidation.constraint.to.multipartfileimage.ImageAcceptRgbTo;
import com.teammental.mevalidation.constraint.to.multipartfileimage.ImageMaxHeight500To;
import com.teammental.mevalidation.constraint.to.multipartfileimage.ImageMaxHeight591To;
import com.teammental.mevalidation.constraint.to.multipartfileimage.ImageMaxHeight600To;
import com.teammental.mevalidation.constraint.to.multipartfileimage.ImageMaxWidth500To;
import com.teammental.mevalidation.constraint.to.multipartfileimage.ImageMaxWidth591To;
import com.teammental.mevalidation.constraint.to.multipartfileimage.ImageMaxWidth600To;
import com.teammental.mevalidation.constraint.to.multipartfileimage.ImageMinHeight500To;
import com.teammental.mevalidation.constraint.to.multipartfileimage.ImageMinHeight591To;
import com.teammental.mevalidation.constraint.to.multipartfileimage.ImageMinHeight600To;
import com.teammental.mevalidation.constraint.to.multipartfileimage.ImageMinWidth500To;
import com.teammental.mevalidation.constraint.to.multipartfileimage.ImageMinWidth591To;
import com.teammental.mevalidation.constraint.to.multipartfileimage.ImageMinWidth600To;
import com.teammental.mevalidation.constraint.to.multipartfileimage.ImageNoDpiTo;
import com.teammental.mevalidation.constraint.to.multipartfileimage.ImageOnlyTo;
import com.teammental.mevalidation.constraint.to.multipartfileimage.ImageRejectRgbTo;

@RunWith(Enclosed.class)
public class MultipartFileImageConstraintSingleValidatorTest {

  private static Validator validator;

  private final static Path IMAGE_300_DPI =
      Paths.get("src", "test", "resources", "images", "300dpi_rgb_591_591.jpg");

  private final static Path IMAGE_RGB =
      Paths.get("src", "test", "resources", "images", "300dpi_rgb_591_591.jpg");

  private final static Path IMAGE_HEIGHT_591 =
      Paths.get("src", "test", "resources", "images", "300dpi_rgb_591_591.jpg");

  private final static Path IMAGE_WIDTH_591 =
      Paths.get("src", "test", "resources", "images", "300dpi_rgb_591_591.jpg");

  abstract static class SharedSetUp {

    @Before
    public void setUp() {
      ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
      validator = validatorFactory.getValidator();
    }

  }


  public static class ImageOnlyTest extends SharedSetUp {

    @Test
    public void shouldNotValidateWhenFileIsNotImage() throws IOException {

      MultipartFile multipartFile =
          new MockMultipartFile("test.jpg", "test.jpg", "image/jpg", new byte[] {0,1,0,1});

      ImageOnlyTo to = new ImageOnlyTo();
      to.setMultipartFile(multipartFile);

      Set violations = validator.validate(to);

      assertFalse(violations.isEmpty());
    }
  }

  public static class DpiTest extends SharedSetUp {

    @Test
    public void shouldNotValidateWhenDpiIsDifferentThanAccepted() throws IOException {

      MultipartFile multipartFile = prepareMultipartFile(IMAGE_300_DPI);

      Image72dpiTo to = new Image72dpiTo();
      to.setMultipartFile(multipartFile);

      Set violations = validator.validate(to);

      assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldValidateWhenAcceptedDpiIsLowerThanZero() throws IOException {

      MultipartFile multipartFile = prepareMultipartFile(IMAGE_300_DPI);

      ImageNoDpiTo to = new ImageNoDpiTo();
      to.setMultipartFile(multipartFile);

      Set violations = validator.validate(to);

      assertTrue(violations.isEmpty());
    }

    @Test
    public void shouldValidateWhenDpiIsSameAsAccepted() throws IOException {

      MultipartFile multipartFile = prepareMultipartFile(IMAGE_300_DPI);

      Image300DpiTo to = new Image300DpiTo();
      to.setMultipartFile(multipartFile);

      Set violations = validator.validate(to);

      assertTrue(violations.isEmpty());
    }


  }

  public static class RgbTest extends SharedSetUp {

    @Test
    public void shouldNotValidate_whenImageIsRgbAndRgbIsRejected() throws IOException {

      MultipartFile multipartFile = prepareMultipartFile(IMAGE_RGB);

      ImageRejectRgbTo imageRejectRgbTo = new ImageRejectRgbTo();
      imageRejectRgbTo.setMultipartFile(multipartFile);

      Set violations = validator.validate(imageRejectRgbTo);

      assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldValidate_whenImageIsRgbAndRgbIsAccepted() throws IOException {
      MultipartFile multipartFile = prepareMultipartFile(IMAGE_RGB);

      ImageAcceptRgbTo imageAcceptRgbTo = new ImageAcceptRgbTo();
      imageAcceptRgbTo.setMultipartFile(multipartFile);

      Set violations = validator.validate(imageAcceptRgbTo);

      assertTrue(violations.isEmpty());
    }

    @Test
    public void shouldValidate_whenImageIsRgbAndAnyColorTypeIsAccepted() throws IOException {
      MultipartFile multipartFile = prepareMultipartFile(IMAGE_RGB);

      ImageOnlyTo imageOnlyTo = new ImageOnlyTo();
      imageOnlyTo.setMultipartFile(multipartFile);

      Set violations = validator.validate(imageOnlyTo);

      assertTrue(violations.isEmpty());
    }
  }

  public static class MaxHeightTest extends SharedSetUp {

    @Test
    public void shouldNotValidate_whenImageHeightIsHigherThanAccepted() throws IOException {

      MultipartFile multipartFile = prepareMultipartFile(IMAGE_HEIGHT_591);

      ImageMaxHeight500To imageMaxHeight500To = new ImageMaxHeight500To();
      imageMaxHeight500To.setMultipartFile(multipartFile);

      Set violations = validator.validate(imageMaxHeight500To);

      assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldValidate_whenImageHeightEqualsToAccepted() throws IOException {

      MultipartFile multipartFile = prepareMultipartFile(IMAGE_HEIGHT_591);

      ImageMaxHeight591To imageMaxHeight591To = new ImageMaxHeight591To();
      imageMaxHeight591To.setMultipartFile(multipartFile);

      Set violations = validator.validate(imageMaxHeight591To);

      assertTrue(violations.isEmpty());
    }

    @Test
    public void shouldValidate_whenImageHeightIsLowerThenAccepted() throws IOException {

      MultipartFile multipartFile = prepareMultipartFile(IMAGE_HEIGHT_591);

      ImageMaxHeight600To imageMaxHeight600To = new ImageMaxHeight600To();
      imageMaxHeight600To.setMultipartFile(multipartFile);

      Set violations = validator.validate(imageMaxHeight600To);

      assertTrue(violations.isEmpty());
    }

  }

  public static class MinHeightTest extends SharedSetUp {

    @Test
    public void shouldNotValidate_whenImageHeightIsLowerThenAccepted() throws IOException {

      MultipartFile multipartFile = prepareMultipartFile(IMAGE_HEIGHT_591);

      ImageMinHeight600To imageMinHeight600To = new ImageMinHeight600To();
      imageMinHeight600To.setMultipartFile(multipartFile);

      Set violations = validator.validate(imageMinHeight600To);

      assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldValidate_whenImageHeightEqualsToAccepted() throws IOException {

      MultipartFile multipartFile = prepareMultipartFile(IMAGE_HEIGHT_591);

      ImageMinHeight591To imageMinHeight591To = new ImageMinHeight591To();
      imageMinHeight591To.setMultipartFile(multipartFile);

      Set violations = validator.validate(imageMinHeight591To);

      assertTrue(violations.isEmpty());
    }

    @Test
    public void shouldValidate_whenImageHeightIsHigherThanAccepted() throws IOException {

      MultipartFile multipartFile = prepareMultipartFile(IMAGE_HEIGHT_591);

      ImageMinHeight500To imageMinHeight500To = new ImageMinHeight500To();
      imageMinHeight500To.setMultipartFile(multipartFile);

      Set violations = validator.validate(imageMinHeight500To);

      assertTrue(violations.isEmpty());
    }
  }

  public static class MaxWidthTest extends SharedSetUp {

    @Test
    public void shouldNotValidate_whenImageWidthIsHigherThanAccepted() throws IOException {
      MultipartFile  multipartFile = prepareMultipartFile(IMAGE_WIDTH_591);

      ImageMaxWidth500To imageMaxWidth500To = new ImageMaxWidth500To();
      imageMaxWidth500To.setMultipartFile(multipartFile);

      Set violations = validator.validate(imageMaxWidth500To);

      assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldValidate_whenImageWidthEqualsToAccepted() throws IOException {

      MultipartFile multipartFile = prepareMultipartFile(IMAGE_WIDTH_591);

      ImageMaxWidth591To imageMaxWidth591To = new ImageMaxWidth591To();
      imageMaxWidth591To.setMultipartFile(multipartFile);

      Set violations = validator.validate(imageMaxWidth591To);

      assertTrue(violations.isEmpty());
    }


    @Test
    public void shouldValidate_whenImageWidthIsLowerThanAccepted() throws IOException {

      MultipartFile multipartFile = prepareMultipartFile(IMAGE_WIDTH_591);

      ImageMaxWidth600To imageMaxWidth600To = new ImageMaxWidth600To();
      imageMaxWidth600To.setMultipartFile(multipartFile);

      Set violations = validator.validate(imageMaxWidth600To);

      assertTrue(violations.isEmpty());
    }
  }

  public static class MinWidthTest extends SharedSetUp {

    @Test
    public void shouldNotValidate_whenImageWidthIsLowerThanAccepted() throws IOException {

      MultipartFile multipartFile = prepareMultipartFile(IMAGE_WIDTH_591);

      ImageMinWidth600To imageMinWidth600To = new ImageMinWidth600To();
      imageMinWidth600To.setMultipartFile(multipartFile);

      Set violations = validator.validate(imageMinWidth600To);

      assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldValidate_whenImageWidthEqualsToAccepted() throws IOException {

      MultipartFile multipartFile = prepareMultipartFile(IMAGE_WIDTH_591);

      ImageMinWidth591To imageMinWidth591To = new ImageMinWidth591To();
      imageMinWidth591To.setMultipartFile(multipartFile);

      Set violations = validator.validate(imageMinWidth591To);

      assertTrue(violations.isEmpty());
    }

    @Test
    public void shouldValidate_whenImageWidthIsHigherThanAccepted() throws IOException {

      MultipartFile multipartFile = prepareMultipartFile(IMAGE_WIDTH_591);

      ImageMinWidth500To imageMinWidth500To = new ImageMinWidth500To();
      imageMinWidth500To.setMultipartFile(multipartFile);

      Set violations = validator.validate(imageMinWidth500To);

      assertTrue(violations.isEmpty());
    }
  }

  private static MultipartFile prepareMultipartFile(Path path) throws IOException {
    byte[] bytes = Files.readAllBytes(path);

    MockMultipartFile multipartFile = new MockMultipartFile("file.jpg", "file.jpg",
        "image/jpg", bytes);
    return multipartFile;
  }
}