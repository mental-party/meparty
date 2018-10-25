package com.teammental.meproxy.ui.editor.dto;

import com.teammental.meproxy.ui.editor.editortemplate.DateTimeEditor;
import com.teammental.meproxy.ui.editor.editortemplate.FileUploadEditor;
import com.teammental.meproxy.ui.editor.editortemplate.FileUploadImageEditor;
import com.teammental.meproxy.ui.editor.editortemplate.SelectSingleFillFromAjaxEditor;
import com.teammental.meproxy.ui.editor.editortemplate.SelectSingleFillFromModelEditor;
import java.lang.annotation.Annotation;

public abstract class EditorFieldDtoFactory {

  /**
   * Get a new instance of {@link EditorFieldDto}.
   *
   * @param annotation annotation
   * @return new instance
   */
  public static EditorFieldDto getEditorField(Annotation annotation) {

    Class<?> type = annotation.annotationType();

    if (type.equals(DateTimeEditor.class)) {
      return new DateTimeEditorFieldDto();
    } else if (type.equals(SelectSingleFillFromModelEditor.class)) {
      return new SelectSingleFillFromModelEditorFieldDto();
    } else if (type.equals(SelectSingleFillFromAjaxEditor.class)) {
      return new SelectSingleFillFromAjaxEditorFieldDto();
    } else if (type.equals(SelectSingleFillByTriggerAjaxEditorFieldDto.class)) {
      return new SelectSingleFillByTriggerAjaxEditorFieldDto();
    } else if (type.equals(FileUploadEditor.class)) {
      return new FileUploadEditorFieldDto();
    } else if (type.equals(FileUploadImageEditor.class)) {
      return new FileImageUploadEditorFieldDto();
    }

    return new EditorFieldDto();
  }
}
