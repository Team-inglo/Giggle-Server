package com.inglo.giggle.owner.adapter.out.persistence.repository.mysql;

import com.inglo.giggle.owner.adapter.out.persistence.entity.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OwnerJpaRepository extends JpaRepository<OwnerEntity, UUID>{
}
