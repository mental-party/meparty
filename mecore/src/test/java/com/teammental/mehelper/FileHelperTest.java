package com.teammental.mehelper;

import static com.teammental.mehelper.FileHelper.getExtension;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.teammental.mecore.enums.FileExtension;
import java.util.Locale;
import org.junit.Test;

public class FileHelperTest {

  public static class GetExtension {

    @Test
    public void shouldReturnEmpty_whenNoExtensionFound() {
      assertEquals("", getExtension("noExtension"));
    }

    @Test
    public void shouldReturnExtension_whenSingleFileNameIsSupplied() {
      assertEquals("ext", getExtension("file.ext"));
    }

    @Test
    public void shouldReturnExtension_whenFullFileNameIsSupplied() {
      assertEquals("ext", getExtension("path/to/file.ext"));
    }

    @Test
    public void shouldReturnEmpty_whenPathIsDirectory() {
      assertEquals("", getExtension("A/B/C.ext/"));
    }

    @Test
    public void shouldReturnEmpty_whenPathEndsWithTwoDots() {
      assertEquals("", getExtension("A/B/C.ext/.."));
    }

    @Test
    public void shouldReturnExtension_whenFileNameStartsWithDot() {
      assertEquals("hidden", getExtension(".hidden"));
    }

    @Test
    public void shouldReturnExtension_whenPathContainsReverseSlash() {
      assertEquals("exe", getExtension("C:\\Program Files (x86)\\java\\bin\\javaw.exe"));
    }

    @Test
    public void shouldReturnEmpty_whenFileNameIsEmpty() {
      assertEquals("", getExtension(""));
    }

    @Test
    public void shouldReturnEmpty_whenFileNameIsNull() {
      assertEquals("", getExtension(null));
    }

    @Test
    public void shouldLowerCaseWithEnglishLocale() {
      Locale localeOriginal = Locale.getDefault();
      Locale.setDefault(new Locale("tr", "TR"));

      assertNotEquals("bın", getExtension("file.BIN"));

      Locale.setDefault(localeOriginal);
    }
  }

  public static class GetFileExtensionByFileName {
    @Test
    public void shouldReturnFileExtension_whenKnown_1() {
      final String fileName = "file.jpg";
      final FileExtension expectedFileExtension = FileExtension.JPG;

      final FileExtension actualFileExtension = FileHelper.getFileExtension(fileName);

      assertEquals(expectedFileExtension, actualFileExtension);
    }

    @Test
    public void shouldReturnFileExtension_whenKnown_2() {
      final String fileName = "file.pdf";
      final FileExtension expectedFileExtension = FileExtension.PDF;

      final FileExtension actualFileExtension = FileHelper.getFileExtension(fileName);

      assertEquals(expectedFileExtension, actualFileExtension);
    }

    @Test
    public void shouldReturnFileExtensionUnknown_whenNotKnown() {
      final String fileName = "file.__432gf3w_-_";
      final FileExtension expectedFileExtension = FileExtension.UNKNOWN;

      final FileExtension actualFileExtension = FileHelper.getFileExtension(fileName);

      assertEquals(expectedFileExtension, actualFileExtension);
    }

    @Test
    public void shouldReturnFileExtensionUnknown_whenEMPTY() {
      final String fileName = "file";
      final FileExtension expectedFileExtension = FileExtension.UNKNOWN;

      final FileExtension actualFileExtension = FileHelper.getFileExtension(fileName);

      assertEquals(expectedFileExtension, actualFileExtension);
    }
  }

  public static class GetFileExtensionByExtensionId {

    @Test
    public void shouldReturnFileExtension_whenKnown_1() {
      final int extensionId = FileExtension.BMP.getExtensionId();
      final FileExtension expectedFileExtension = FileExtension.BMP;

      final FileExtension actualFileExtension = FileHelper.getFileExtension(extensionId);

      assertEquals(expectedFileExtension, actualFileExtension);
    }

    @Test
    public void shouldReturnFileExtension_whenKnown_2() {
      final int extensionId = FileExtension.MP4.getExtensionId();
      final FileExtension expectedFileExtension = FileExtension.MP4;

      final FileExtension actualFileExtension = FileHelper.getFileExtension(extensionId);

      assertEquals(expectedFileExtension, actualFileExtension);
    }

    @Test
    public void shouldReturnFileExtensionUnknown_whenNotKnown() {
      final int extensionId = Integer.MIN_VALUE + 1;

      final FileExtension expectedFileExtension = FileExtension.UNKNOWN;

      final FileExtension actualFileExtension = FileHelper.getFileExtension(extensionId);

      assertEquals(expectedFileExtension, actualFileExtension);
    }

  }
}