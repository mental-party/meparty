package com.teammental.meproxy.ui.editor.dto;

import com.teammental.mecore.stereotype.dto.Dto;
import java.util.List;

public class EditorViewDto implements Dto {

  private List<EditorFieldDto> editorFieldDtos;

  public List<EditorFieldDto> getEditorFieldDtos() {

    return editorFieldDtos;
  }

  public void setEditorFieldDtos(
      List<EditorFieldDto> editorFieldDtos) {

    this.editorFieldDtos = editorFieldDtos;
  }
}
