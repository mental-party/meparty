package com.teammental.mecontroller.rest;

import com.teammental.medto.IdDto;
import com.teammental.meexception.dto.DtoCrudException;
import com.teammental.meservice.BaseCrudService;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface BaseCrudRestApi<ServiceT extends BaseCrudService,
    DtoT extends IdDto,
    IdT extends Serializable> {
  /**
   * Get all DtoT items.
   *
   * @return HttpStatus=200, List of DtoT objects
   * @throws DtoCrudException if fails
   */
  @GetMapping("")
  ResponseEntity<List<DtoT>> getAll();

  @GetMapping("/{id}")
  ResponseEntity<DtoT> getById(@PathVariable(value = "id") final IdT id);

  /**
   * Insert a new DtoT item.
   *
   * @param dto DtoT object to be inserted
   * @return HttpStatus=201, Location of newly created item's detail url
   * @throws DtoCrudException if fails
   */
  @PostMapping()
  ResponseEntity create(@Validated @RequestBody final DtoT dto);

  /**
   * Update a DtoT item.
   *
   * @param dto DtoT object to be updated.
   * @return HttpStatus=200, DtoT object newly updated
   * @throws DtoCrudException if fails
   */
  @PutMapping()
  ResponseEntity<IdT> update(@Validated @RequestBody final DtoT dto);

  /**
   * Delete a DtoT item.
   *
   * @param id id of the DtoT item to be deleted
   * @return HttpStatus=204
   * @throws DtoCrudException if fails
   */
  @DeleteMapping("/{id}")
  ResponseEntity delete(@PathVariable(value = "id") final IdT id);
}
