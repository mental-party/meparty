package com.teammental.meservice;

import com.teammental.mecore.stereotype.entity.Entity;
import com.teammental.medto.FilterDto;
import com.teammental.medto.IdDto;
import com.teammental.meexception.dto.DtoCreateException;
import com.teammental.meexception.dto.DtoCrudException;
import com.teammental.meexception.dto.DtoDeleteException;
import com.teammental.meexception.dto.DtoNotFoundException;
import com.teammental.meexception.dto.DtoUpdateException;
import com.teammental.memapper.types.Mapper;
import com.teammental.merepository.BaseJpaRepository;
import java.io.Serializable;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class BaseCrudServiceImpl<DtoT extends IdDto<IdT>, IdT extends Serializable>
    implements BaseCrudService<DtoT, IdT> {

  private static final Logger LOGGER = LoggerFactory.getLogger(BaseCrudServiceImpl.class);

  @Autowired
  private Mapper mapper;

  // region override methods

  @Override
  public final Page<DtoT> findAll() throws DtoCrudException {
    return findAll(null);
  }

  @Override
  public final Page<DtoT> findAll(FilterDto filterDto) throws DtoCrudException {
    LOGGER.debug("findAll of " + getDtoClass().getSimpleName() + " started");
    try {
      Page<DtoT> dtos = doFindAll(filterDto);
      LOGGER.debug("findAll of " + getDtoClass().getSimpleName() + " ended");
      return dtos;
    } catch (DtoCrudException ex) {
      LOGGER.error("findAll of " + getDtoClass().getSimpleName()
          + " throwed a DtoCrudException");
      throw ex;
    }
  }

  @Override
  public final DtoT findById(final IdT id) throws DtoCrudException {
    LOGGER.debug("findById of " + getDtoClass().getSimpleName()
        + " started, with parameter: id=" + id);
    try {
      DtoT dto = doFindById(id);
      LOGGER.debug("findById of " + getDtoClass().getSimpleName()
          + " ended, with parameter: id=" + id);
      return dto;
    } catch (DtoCrudException ex) {
      LOGGER.error("findById of " + getDtoClass().getSimpleName()
          + " throwed a DtoCrudException");
      throw ex;
    }
  }

  @Override
  public final IdT save(final DtoT dto) throws DtoCrudException {

    if (dto == null) {
      return null;
    }

    if (dto.getId() == null) {
      LOGGER.debug("create of " + getDtoClass().getSimpleName()
          + " started, with parameter: dto=" + dto.toString());
      try {
        IdT id = doCreate(dto);
        LOGGER.debug("create of " + getDtoClass().getSimpleName() + " ended");
        return id;
      } catch (DtoCrudException ex) {
        LOGGER.error("insert of " + getDtoClass().getSimpleName()
            + " throwed a DtoCrudException");
        throw ex;
      }
    } else {
      LOGGER.debug("update of " + getDtoClass().getSimpleName()
          + " started, with parameter: dto=" + dto.toString());
      try {
        IdT idResult = doUpdate(dto);
        LOGGER.debug("update of " + getDtoClass().getSimpleName() + " ended");
        return idResult;
      } catch (DtoCrudException ex) {
        LOGGER.error("update of " + getDtoClass().getSimpleName()
            + " throwed an DtoCrudException");
        throw ex;
      }
    }

  }

  @Override
  public final boolean delete(final IdT id) throws DtoCrudException {
    LOGGER.debug("delete of " + getDtoClass().getSimpleName()
        + " started, with parameter: id=" + id.toString());
    try {
      boolean result = doDelete(id);
      LOGGER.debug("delete of " + getDtoClass().getSimpleName() + " ended");
      return result;
    } catch (DtoCrudException ex) {
      LOGGER.error("delete of " + getDtoClass().getSimpleName()
          + " throwed a DtoCrudException");
      throw ex;
    }
  }

  // endregion override methods

  // region protected default methods

  protected Page<DtoT> doFindAll(FilterDto filterDto) throws DtoCrudException {

    Page entities;
    if (filterDto == null) {
      entities = new PageImpl<>(getRepository().findAll());
    } else {
      entities = getRepository().findAll(filterDto.getPage().toPageRequest());
    }

    if (!entities.hasContent()) {
      throw new DtoNotFoundException();
    }

    return (Page<DtoT>) entities.map(p -> mapper.map(p, getDtoClass()));
  }

  protected DtoT doFindById(final IdT id) throws DtoCrudException {

    Optional optionalEntity = getRepository().findById(id);

    if (!optionalEntity.isPresent()) {
      throw new DtoNotFoundException();
    }
    return mapper.map(optionalEntity.get(), getDtoClass());

  }

  protected IdT doCreate(final DtoT dto) throws DtoCrudException {

    try {

      Object entity = mapper.map(dto, getEntityClass());

      entity = getRepository().save(entity);

      DtoT dtoResult = mapper.map(entity, getDtoClass());

      return dtoResult.getId();

    } catch (Exception ex) {
      throw new DtoCreateException(dto, ex);
    }

  }

  protected IdT doUpdate(final DtoT dto) throws DtoCrudException {
    Optional optionalEntity = getRepository().findById(dto.getId());

    if (!optionalEntity.isPresent()) {
      throw new DtoNotFoundException();
    }

    Object entity = mapper.map(dto, optionalEntity.get());

    try {
      Object resultEntity = getRepository().save(entity);

      DtoT dtoResult = mapper.map(resultEntity, getDtoClass());

      return dtoResult.getId();
    } catch (Exception ex) {
      throw new DtoUpdateException(dto, ex);
    }
  }

  protected boolean doDelete(final IdT id) throws DtoCrudException {

    try {
      getRepository().deleteById(id);
      return true;
    } catch (Exception ex) {
      throw new DtoDeleteException(ex);
    }

  }

  // endregion protected default methods

  // region abstract methods

  protected abstract BaseJpaRepository getRepository();

  protected abstract Class<? extends DtoT> getDtoClass();

  protected abstract Class<? extends Entity> getEntityClass();

  // endregion abstract methods
}
