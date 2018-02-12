package com.teammental.medto;

import com.teammental.mecore.stereotype.dto.Dto;
import com.teammental.medto.page.PageRequestDto;

public interface FilterDto extends Dto {

  PageRequestDto getPage();
}
