package com.inglo.giggle.document.adapter.out.persistence.repository.mysql;

import com.inglo.giggle.document.adapter.out.persistence.entity.RejectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RejectJpaRepository extends JpaRepository<RejectEntity, Long>{
    List<RejectEntity> findAllByDocumentEntityId(Long id);
}
