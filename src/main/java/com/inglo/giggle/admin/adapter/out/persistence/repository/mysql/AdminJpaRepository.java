package com.inglo.giggle.admin.adapter.out.persistence.repository.mysql;

import com.inglo.giggle.admin.adapter.out.persistence.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdminJpaRepository extends JpaRepository<AdminEntity, UUID> {
}
