package com.inglo.giggle.document.persistence.repository.mysql;

import com.inglo.giggle.document.persistence.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentJpaRepository extends JpaRepository<DocumentEntity, Long>{
}
