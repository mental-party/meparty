package com.teammental.service;

import com.teammental.exception.dto.DtoCrudException;
import com.teammental.dto.IdDto;

import java.io.Serializable;
import java.util.List;

public interface BaseCrudService<DtoT extends IdDto<IdT>, IdT extends Serializable> {
  List<DtoT> findAll() throws DtoCrudException;

  DtoT findById(IdT id) throws DtoCrudException;

  IdT create(DtoT dto) throws DtoCrudException;

  IdT update(DtoT dto) throws DtoCrudException;

  boolean delete(IdT id) throws DtoCrudException;
}
