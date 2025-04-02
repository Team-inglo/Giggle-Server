package com.inglo.giggle.document.persistence.repository;

import com.inglo.giggle.document.domain.Document;

public interface DocumentRepository {

    Document findByIdOrElseThrow(Long id);

    Document save(Document document);
}
