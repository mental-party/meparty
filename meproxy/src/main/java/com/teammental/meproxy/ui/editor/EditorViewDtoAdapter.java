package com.teammental.meproxy.ui.editor;

import com.teammental.mecore.stereotype.dto.Dto;
import com.teammental.meproxy.ui.editor.dto.EditorViewDto;

public interface EditorViewDtoAdapter {

  <T extends Dto> EditorViewDto adapt(T dto);
}
