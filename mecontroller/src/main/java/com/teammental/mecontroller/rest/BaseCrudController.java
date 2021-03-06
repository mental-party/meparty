package com.teammental.mecontroller.rest;

import com.teammental.mecontroller.BaseController;
import com.teammental.medto.FilterDto;
import com.teammental.medto.IdDto;
import com.teammental.meexception.dto.DtoCrudException;
import com.teammental.merest.RestResponse;
import com.teammental.meservice.BaseCrudService;
import java.io.Serializable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class BaseCrudController<ServiceT extends BaseCrudService,
    DtoT extends IdDto,
    IdT extends Serializable>
    extends BaseController
    implements BaseCrudRestApi<DtoT, IdT> {


  // region request methods

  /**
   * {@inheritDoc}
   */
  @Override
  public final RestResponse<Page<DtoT>> getAll(
      @RequestBody(required = false) final FilterDto filterDto)
      throws DtoCrudException {

    Page<DtoT> dtos = doGetAll(filterDto);

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
  public final RestResponse<IdT> save(@Validated @RequestBody final DtoT dto)
      throws DtoCrudException {
    IdT id = doSave(dto);

    return RestResponse.of(ResponseEntity
        .status(HttpStatus.OK)
        .body(id));
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

  protected Page<DtoT> doGetAll(final FilterDto filterDto) throws DtoCrudException {
    Page<DtoT> dtos;
    if (filterDto == null) {
      dtos = getCrudService().findAll();
    } else {
      dtos = getCrudService().findAll(filterDto);
    }
    return dtos;
  }

  protected DtoT doGetById(final IdT id) throws DtoCrudException {
    DtoT dtoResult = (DtoT) getCrudService().findById(id);
    return dtoResult;
  }

  protected IdT doSave(final DtoT dto) throws DtoCrudException {
    IdT id = (IdT)getCrudService().save(dto);
    return id;
  }


  protected boolean doDelete(final IdT id) throws DtoCrudException {
    boolean result = getCrudService().delete(id);
    return result;
  }

  // region abstract methods

  protected abstract ServiceT getCrudService();

  // endregion abstract methods

  // endregion
}
