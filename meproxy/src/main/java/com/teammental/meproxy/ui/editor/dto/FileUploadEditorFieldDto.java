package com.teammental.meproxy.ui.editor.dto;

public class FileUploadEditorFieldDto extends EditorFieldDto {

  private byte[] existingFile;

  /**
   * Getter.
   *
   * @return byte array
   */
  public byte[] getExistingFile() {

    if (existingFile == null) {
      return null;
    }
    return existingFile.clone();
  }

  /**
   * Setter.
   *
   * @param existingFile byte array
   */
  public void setExistingFile(byte[] existingFile) {

    if (existingFile == null) {
      return;
    }
    this.existingFile = existingFile.clone();
  }
}
