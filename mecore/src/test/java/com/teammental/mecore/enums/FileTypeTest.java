package com.teammental.mecore.enums;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.EnumSet;
import org.junit.Test;

public class FileTypeTest {

  @Test
  public void fileTypes_shouldHaveAtLeastOneExtension() {
    final FileType[] fileTypes = FileType.values();

    for (FileType fileType :
        fileTypes) {
      EnumSet<FileExtension> fileExtensions = fileType.getFileExtensions();
      assertNotNull(fileExtensions);
      assertTrue(fileExtensions.size() > 0);
    }
  }
}