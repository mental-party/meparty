package com.teammental.medto;

import com.teammental.mecore.stereotype.dto.Dto;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;

public interface FilterDto<DtoT extends Dto> extends Dto {

  Pageable getPage();

  Example<DtoT> getExample();
}
