package com.teammental.mecontroller.rest;

import com.teammental.mecore.stereotype.controller.Controller;
import com.teammental.medto.FilterDto;
import com.teammental.medto.IdDto;
import com.teammental.meexception.dto.DtoCrudException;
import com.teammental.merest.RestResponse;
import java.io.Serializable;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface BaseCrudRestApi<
    DtoT extends IdDto,
    IdT extends Serializable>
    extends Controller {

  /**
   * Get all DtoT items.
   *
   * @param filterDto for filtering items
   * @return HttpStatus=200, List of DtoT objects
   * @throws DtoCrudException if fails
   */
  @PostMapping(value = "/filter")
  RestResponse<Page<DtoT>> getAll(@RequestBody(required = false) final FilterDto filterDto);

  @GetMapping("/{id}")
  RestResponse<DtoT> getById(@PathVariable(value = "id") final IdT id);

  /**
   * Insert or update a new DtoT item.
   *
   * @param dto DtoT object to be inserted
   * @return HttpStatus=200, Id in body
   * @throws DtoCrudException if fails
   */
  @PostMapping()
  RestResponse<IdT> save(@Validated @RequestBody final DtoT dto);

  /**
   * Delete a DtoT item.
   *
   * @param id id of the DtoT item to be deleted
   * @return HttpStatus=204
   * @throws DtoCrudException if fails
   */
  @DeleteMapping("/{id}")
  RestResponse delete(@PathVariable(value = "id") final IdT id);
}