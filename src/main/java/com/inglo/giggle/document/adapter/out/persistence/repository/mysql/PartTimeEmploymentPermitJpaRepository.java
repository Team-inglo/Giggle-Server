package com.inglo.giggle.document.adapter.out.persistence.repository.mysql;

import com.inglo.giggle.document.adapter.out.persistence.entity.PartTimeEmploymentPermitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartTimeEmploymentPermitJpaRepository extends JpaRepository<PartTimeEmploymentPermitEntity, Long>{
    Optional<PartTimeEmploymentPermitEntity> findByUserOwnerJobPostingId(Long userOwnerJobPostingId);
}
