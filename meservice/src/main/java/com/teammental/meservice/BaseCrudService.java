package com.teammental.meservice;

import com.teammental.medto.FilterDto;
import com.teammental.medto.IdDto;
import com.teammental.meexception.dto.DtoCrudException;
import java.io.Serializable;
import org.springframework.data.domain.Page;

public interface BaseCrudService<DtoT extends IdDto<IdT>, IdT extends Serializable> {
  Page<DtoT> findAll() throws DtoCrudException;

  Page<DtoT> findAll(FilterDto filterDto) throws DtoCrudException;

  DtoT findById(IdT id) throws DtoCrudException;

  IdT save(DtoT dto) throws DtoCrudException;

  boolean delete(IdT id) throws DtoCrudException;
}