package com.inglo.giggle.document.adapter.out.persistence.repository.mysql;

import com.inglo.giggle.document.adapter.out.persistence.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentJpaRepository extends JpaRepository<DocumentEntity, Long>{
}
