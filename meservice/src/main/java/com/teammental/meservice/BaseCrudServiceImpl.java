package com.teammental.meservice;

import com.teammental.mecore.entity.Entity;
import com.teammental.medto.IdDto;
import com.teammental.meexception.dto.DtoCreateException;
import com.teammental.meexception.dto.DtoCrudException;
import com.teammental.meexception.dto.DtoDeleteException;
import com.teammental.meexception.dto.DtoNotFoundException;
import com.teammental.meexception.dto.DtoUpdateException;
import com.teammental.memapper.MeMapper;
import com.teammental.memapper.util.mapping.MapByFieldNameUtil;
import com.teammental.merepository.BaseJpaRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class BaseCrudServiceImpl<DtoT extends IdDto<IdT>, IdT extends Serializable>
    implements BaseCrudService<DtoT, IdT> {

  private static final Logger LOGGER = LoggerFactory.getLogger(BaseCrudServiceImpl.class);

  // region override methods

  @Override
  public final List<DtoT> findAll() throws DtoCrudException {
    LOGGER.debug("findAll of " + getDtoClass().getSimpleName() + " started");
    try {
      List<DtoT> dtos = doFindAll();
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
  public final IdT create(final DtoT dto) throws DtoCrudException {
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
  }

  @Override
  public final IdT update(final DtoT dto)
      throws DtoCrudException {
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

  protected List<DtoT> doFindAll() throws DtoCrudException {

    List entities = getRepository().findAll();

    Optional<List<DtoT>> optionalDtos = MeMapper.from(entities)
        .toOptional(getDtoClass());

    if (!optionalDtos.isPresent() || optionalDtos.get().isEmpty()) {
      throw new DtoNotFoundException();
    }

    return optionalDtos.get();
  }

  protected DtoT doFindById(final IdT id) throws DtoCrudException {

    Optional optionalEntity = getRepository().findById(id);
    Optional<DtoT> dto = MeMapper.from(optionalEntity.get()).toOptional(getDtoClass());

    if (!dto.isPresent()) {
      throw new DtoNotFoundException();
    }

    return dto.get();
  }

  protected IdT doCreate(final DtoT dto) throws DtoCrudException {

    Optional optionalEntity = MeMapper.from(dto)
        .toOptional(getEntityClass());

    try {
      Object entity = getRepository().save(optionalEntity.get());
      Optional<DtoT> optionalDto = MeMapper.from(entity)
          .toOptional(getDtoClass());

      return optionalDto.get().getId();
    } catch (Exception ex) {
      throw new DtoCreateException(dto, ex);
    }

  }

  protected IdT doUpdate(final DtoT dto) throws DtoCrudException {
    Optional optionalEntity = getRepository().findById(dto.getId());

    if (!optionalEntity.isPresent()) {
      throw new DtoNotFoundException();
    }

    Object entity = MapByFieldNameUtil.map(dto, optionalEntity.get());

    try {
      Object resultEntity = getRepository().save(entity);
      Optional<DtoT> optionalDto = MeMapper.from(resultEntity)
          .toOptional(getDtoClass());

      return optionalDto.get().getId();
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
