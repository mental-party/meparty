package com.teammental.mecontroller;

import com.teammental.medto.IdDto;
import com.teammental.meexception.dto.DtoCrudException;
import com.teammental.meservice.BaseCrudService;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class BaseCrudController<ServiceT extends BaseCrudService,
    DtoT extends IdDto,
    IdT extends Serializable> extends BaseController {


  // region request methods

  /**
   * Get all DtoT items.
   *
   * @return HttpStatus=200, List of DtoT objects
   * @throws DtoCrudException if fails
   */
  @GetMapping("")
  public final ResponseEntity getAll() throws DtoCrudException {
    final List<DtoT> dtos = doGetAll();
    return ResponseEntity.ok(dtos);
  }

  @GetMapping("/{id}")
  public final ResponseEntity getById(@PathVariable(value = "id") final IdT id)
      throws DtoCrudException {
    DtoT dto = doGetById(id);
    return ResponseEntity.ok(dto);
  }

  /**
   * Insert a new DtoT item.
   *
   * @param dto DtoT object to be inserted
   * @return HttpStatus=201, Location of newly created item's detail url
   * @throws DtoCrudException if fails
   */
  @PostMapping()
  public final ResponseEntity create(@Validated @RequestBody final DtoT dto)
      throws DtoCrudException {
    Serializable id = doInsert(dto);
    String location = getMappingUrlOfController() + "/" + id.toString();

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .header("Location", location)
        .build();
  }

  /**
   * Update a DtoT item.
   *
   * @param dto DtoT object to be updated.
   * @return HttpStatus=200, DtoT object newly updated
   * @throws DtoCrudException if fails
   */
  @PutMapping()
  public final ResponseEntity update(@Validated @RequestBody final DtoT dto)
      throws DtoCrudException {
    DtoT dtoResult = doUpdate(dto);
    return ResponseEntity.ok(dtoResult);
  }

  /**
   * Delete a DtoT item.
   *
   * @param id id of the DtoT item to be deleted
   * @return HttpStatus=204
   * @throws DtoCrudException if fails
   */
  @DeleteMapping("/{id}")
  public final ResponseEntity delete(@PathVariable(value = "id") final IdT id)
      throws DtoCrudException {
    boolean result = doDelete(id);
    if (result) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
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

  protected DtoT doUpdate(final DtoT dto) throws DtoCrudException {
    DtoT dtoResult = (DtoT) getCrudService().update(dto);
    return dtoResult;
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
