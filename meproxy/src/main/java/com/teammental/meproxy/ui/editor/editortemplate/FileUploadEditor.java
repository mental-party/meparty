package com.teammental.meproxy.ui.editor.editortemplate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@EditorTemplate(name = "fileupload")
@Target(ElementType.FIELD)
public @interface FileUploadEditor {

  /**
   * byte[] olarak data içeren field ismi.
   * @return isim.
   */
  String existingFileFieldName();
}
