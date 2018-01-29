package com.teammental.mehelper;

import static com.teammental.mehelper.FileHelper.getExtension;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
}