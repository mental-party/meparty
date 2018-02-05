package com.teammental.mevalidation.constraint.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.teammental.mehelper.ByteHelper;
import com.teammental.mevalidation.constraint.to.Config;
import com.teammental.mevalidation.constraint.to.multipartfile.FileNotRequiredTo;
import com.teammental.mevalidation.constraint.to.multipartfile.FileRequiredTo;
import com.teammental.mevalidation.constraint.to.multipartfile.FileTxtTo;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@RunWith(Enclosed.class)
public class MultipartFileConstraintSingleValidatorTest {

  private static Validator validator;

  abstract static class SharedSetUp {

    @Before
    public void setUp() {
      ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
      validator = validatorFactory.getValidator();
    }

  }


  public static class Required extends SharedSetUp {

    @Test
    public void shouldNotValidate_whenFieldIsNullAndRequired() {
      FileRequiredTo fileTo = new FileRequiredTo();

      Set<ConstraintViolation<FileRequiredTo>> violations = validator.validate(fileTo);

      assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldValidate_whenFieldIsNotNullAndRequired() {
      MultipartFile multipartFile = prepareMultipartFile("txt");
      FileRequiredTo fileRequiredTo = new FileRequiredTo();
      fileRequiredTo.setMultipartFile(multipartFile);

      Set violations = validator.validate(fileRequiredTo);

      assertTrue(violations.isEmpty());
    }

    @Test
    public void shouldValidate_whenFieldIsNullAndNotRequired() {
      FileNotRequiredTo fileTo = new FileNotRequiredTo();

      Set<ConstraintViolation<FileNotRequiredTo>> violations = validator.validate(fileTo);

      assertTrue(violations.isEmpty());
    }

    @Test
    public void shouldValidate_whenFieldIsNotNullAndNotRequired() {
      MultipartFile multipartFile = prepareMultipartFile("txt");
      FileNotRequiredTo fileTo = new FileNotRequiredTo();
      fileTo.setFile(multipartFile);

      Set<ConstraintViolation<FileNotRequiredTo>> violations = validator.validate(fileTo);

      assertTrue(violations.isEmpty());
    }

  }

  public static class FileExtension extends SharedSetUp {

    @Test
    public void shouldNotValidate_whenFileIsNotNullButExtensionIsDifferent() {
      FileTxtTo fileTxtTo = new FileTxtTo();
      MultipartFile multipartFile = prepareMultipartFile("notTxt");
      fileTxtTo.setFile(multipartFile);

      Set violations = validator.validate(fileTxtTo);

      assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldValidate_whenFileIsNotNullAndExtensionIsSame() {
      FileTxtTo fileTxtTo = new FileTxtTo();
      MultipartFile multipartFile = prepareMultipartFile("txt");
      fileTxtTo.setFile(multipartFile);

      Set violations = validator.validate(fileTxtTo);

      assertTrue(violations.isEmpty());
    }
  }

  public static class MaxSize extends SharedSetUp {

    @Test
    public void shouldNotValidate_whenFileSizeExceeds() {
      FileTxtTo fileTxtTo = new FileTxtTo();
      final int size = (int) Config.MAX_SIZE + 1;
      MultipartFile multipartFile = prepareMultipartFile("txt", size);
      fileTxtTo.setFile(multipartFile);

      Set violations = validator.validate(fileTxtTo);

      assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldValidate_whenFileSizeNotExceeds() {
      FileTxtTo fileTxtTo = new FileTxtTo();
      final int size = (int) Config.MAX_SIZE - 1;
      MultipartFile multipartFile = prepareMultipartFile("txt", size);
      fileTxtTo.setFile(multipartFile);

      Set violations = validator.validate(fileTxtTo);

      assertTrue(violations.isEmpty());
    }
  }

  protected static MultipartFile prepareMultipartFile(String extension) {
    return prepareMultipartFile(extension, 10);
  }

  protected static MultipartFile prepareMultipartFile(String extension, int size) {
    byte[] bytes = ByteHelper.generateRandomByteArray(size);

    MockMultipartFile multipartFile = new MockMultipartFile("file", "file." + extension,
        "text/plain", bytes);
    return multipartFile;
  }

}