package com.teammental.meproxy.ui.editor;

import com.teammental.mecore.stereotype.dto.Dto;
import com.teammental.meproxy.ui.editor.dto.EditorViewDto;

public abstract class AbstractFormViewDto implements Dto {

  private EditorViewDto editorViewDto;

  /**
   * Get editor viewDto.
   *
   * @return viewDto.
   */
  public final EditorViewDto getEditorViewDto() {

    if (editorViewDto == null) {
      EditorViewDtoAdapter adapter
          = new EditorViewDtoAdapterDefaultImpl(getClass());

      editorViewDto = adapter.adapt(this);

    }
    return editorViewDto;
  }
}
