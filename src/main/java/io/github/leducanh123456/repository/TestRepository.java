package io.github.leducanh123456.repository;

import io.github.leducanh123456.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, UUID> {
}
