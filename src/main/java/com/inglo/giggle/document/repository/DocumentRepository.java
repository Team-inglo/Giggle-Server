package com.inglo.giggle.document.repository;

import com.inglo.giggle.document.domain.Document;

public interface DocumentRepository {

    Document findByIdOrElseNull(Long id);

    Document findByIdOrElseThrow(Long id);

    Document findWithUserOwnerJobPostingByIdOrElseThrow(Long id);

    void save(Document document);

    void delete(Document document);

    void deleteById(Long id);
}
