package com.teammental.repository;

import com.teammental.entity.BaseEntity;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseJpaRepository<EntityT extends BaseEntity, IdT extends Serializable>
    extends JpaRepository<EntityT, IdT> {
}
