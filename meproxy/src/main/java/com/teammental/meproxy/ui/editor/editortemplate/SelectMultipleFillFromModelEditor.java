package com.teammental.meproxy.ui.editor.editortemplate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@EditorTemplate(name = "select-multiple-model-attribute")
@Target(ElementType.FIELD)
public @interface SelectMultipleFillFromModelEditor {

  /**
   * Selectin içini modelden doldurmak için attribute belirtilmeli.
   *
   * @return model attribute ismi
   */
  String modelAttributeForSelectList();
}
