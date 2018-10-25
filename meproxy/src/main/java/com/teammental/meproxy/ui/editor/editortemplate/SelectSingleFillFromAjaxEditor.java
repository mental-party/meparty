package com.teammental.meproxy.ui.editor.editortemplate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@EditorTemplate(name = "select-single-from-ajax")
@Target(ElementType.FIELD)
public @interface SelectSingleFillFromAjaxEditor {

  /**
   * Selectin içini ajax ile doldurmak için ajax url verilmeli.
   *
   * @return ajax url
   */
  String ajaxUrl();

  /**
   * Terim ile arama aktifleştirilsin mi.
   *
   * @return boolean
   */
  boolean enableAjaxSearch() default false;
}
