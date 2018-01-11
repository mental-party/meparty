package com.teammental.mecontroller.rest;

import com.teammental.mecontroller.BaseController;
import com.teammental.medto.IdDto;
import com.teammental.meexception.dto.DtoCrudException;
import com.teammental.merest.RestResponse;
import com.teammental.meservice.BaseCrudService;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class BaseCrudController<ServiceT extends BaseCrudService,
    DtoT extends IdDto,
    IdT extends Serializable>
    extends BaseController
    implements BaseCrudRestApi<ServiceT, DtoT, IdT> {


  // region request methods

  /**
   * {@inheritDoc}
   */
  @Override
  public final RestResponse<List<DtoT>> getAll() throws DtoCrudException {
    final List<DtoT> dtos = doGetAll();
    return RestResponse.of(ResponseEntity.ok(dtos));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final RestResponse<DtoT> getById(@PathVariable(value = "id") final IdT id)
      throws DtoCrudException {
    DtoT dto = doGetById(id);
    return RestResponse.of(ResponseEntity.ok(dto));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final RestResponse create(@Validated @RequestBody final DtoT dto)
      throws DtoCrudException {
    Serializable id = doInsert(dto);
    String location = getMappingUrlOfController() + "/" + id.toString();

    return RestResponse.of(ResponseEntity
        .status(HttpStatus.CREATED)
        .header("Location", location)
        .build());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final RestResponse<IdT> update(@Validated @RequestBody final DtoT dto)
      throws DtoCrudException {
    IdT id = (IdT) doUpdate(dto);
    return RestResponse.of(ResponseEntity.ok(id));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final RestResponse delete(@PathVariable(value = "id") final IdT id)
      throws DtoCrudException {
    boolean result = doDelete(id);
    if (result) {
      return RestResponse.of(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    return RestResponse.of(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
  }

  // endregion

  // region protected methods

  protected List<DtoT> doGetAll() throws DtoCrudException {
    List<DtoT> dtos = getCrudService().findAll();
    return dtos;
  }

  protected DtoT doGetById(final IdT id) throws DtoCrudException {
    DtoT dtoResult = (DtoT) getCrudService().findById(id);
    return dtoResult;
  }

  protected Serializable doInsert(final DtoT dto) throws DtoCrudException {
    Serializable id = getCrudService().create(dto);
    return id;
  }

  protected Serializable doUpdate(final DtoT dto) throws DtoCrudException {
    Serializable id = getCrudService().update(dto);
    return id;
  }

  protected boolean doDelete(final IdT id) throws DtoCrudException {
    boolean result = getCrudService().delete(id);
    return result;
  }

  // region abstract methods

  protected abstract ServiceT getCrudService();

  protected abstract String getMappingUrlOfController();

  // endregion abstract methods

  // endregion
}
