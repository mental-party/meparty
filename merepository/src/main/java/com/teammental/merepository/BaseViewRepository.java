package com.teammental.merepository;

import com.teammental.meentity.BaseViewEntity;
import java.io.Serializable;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

@NoRepositoryBean
public interface BaseViewRepository<ViewEntityT extends BaseViewEntity,
    IdT extends Serializable>
    extends Repository<ViewEntityT, IdT>,
    QueryByExampleExecutor<ViewEntityT> {

  /**
   * Retrieves an entity by its id.
   *
   * @param id must not be {@literal null}.
   * @return the entity with the given id or {@literal Optional#empty()} if none found
   * @throws IllegalArgumentException if {@code id} is {@literal null}.
   */
  Optional<ViewEntityT> findById(IdT id);

  /**
   * Returns whether an entity with the given id exists.
   *
   * @param id must not be {@literal null}.
   * @return {@literal true} if an entity with the given id exists, {@literal false} otherwise.
   * @throws IllegalArgumentException if {@code id} is {@literal null}.
   */
  boolean existsById(IdT id);

  /**
   * Returns all instances of the type.
   *
   * @return all entities
   */
  Iterable<ViewEntityT> findAll();

  /**
   * Returns all entities sorted by the given options.
   *
   * @param sort sort
   * @return all entities sorted by the given options
   */
  Iterable<ViewEntityT> findAll(Sort sort);

  /**
   * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code
   * Pageable} object.
   *
   * @param pageable pageable
   * @return a page of entities
   */
  Page<ViewEntityT> findAll(Pageable pageable);

  /**
   * Returns all instances of the type with the given IDs.
   *
   * @param ids ids
   * @return result
   */
  Iterable<ViewEntityT> findAllById(Iterable<IdT> ids);

  /**
   * Returns the number of entities available.
   *
   * @return the number of entities
   */
  long count();

}
