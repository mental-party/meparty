package com.teammental.mecore.enums;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FileExtensionTest {

  @Test
  public void shouldReturnTrue_whenExtensionMatches() {
    final String extension = FileExtension.DOC.getExtensions()[0];

    final boolean matches = FileExtension.DOC.matches(extension);

    assertTrue(matches);
  }

  @Test
  public void shouldReturnFalse_whenExtensionNotMatches() {
    final String extension = "rgegregre";

    final boolean matches = FileExtension.DOC.matches(extension);

    assertFalse(matches);
  }

  @Test
  public void shouldReturnFalse_whenExtensionIsNull() {
    final String extension = null;

    final boolean matches = FileExtension.DOC.matches(extension);

    assertFalse(matches);
  }
}