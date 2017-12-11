package com.teammental.meservice.testapp;

import com.teammental.merepository.BaseJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends BaseJpaRepository<TestEntity, Integer> {
}
