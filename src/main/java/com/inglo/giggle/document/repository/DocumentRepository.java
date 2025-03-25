package com.inglo.giggle.document.repository;

import com.inglo.giggle.document.domain.Document;

public interface DocumentRepository {

    Document findWithUserOwnerJobPostingByIdOrElseThrow(Long id);

    void save(Document document);
}
