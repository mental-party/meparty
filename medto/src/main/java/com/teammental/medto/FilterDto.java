package com.teammental.medto;

import com.teammental.mecore.stereotype.dto.Dto;
import org.springframework.data.domain.Pageable;

public interface FilterDto extends Dto {

  Pageable getPage();
}
