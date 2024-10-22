package com.inglo.giggle.document.repository.mysql;

import com.inglo.giggle.document.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long>{
}
