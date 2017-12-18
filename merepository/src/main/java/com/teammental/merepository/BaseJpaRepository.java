package com.teammental.merepository;

import com.teammental.meentity.BaseEntity;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseJpaRepository<EntityT extends BaseEntity, IdT extends Serializable>
    extends JpaRepository<EntityT, IdT> {
}
