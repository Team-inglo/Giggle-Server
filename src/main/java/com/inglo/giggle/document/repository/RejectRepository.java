package com.inglo.giggle.document.repository;

import com.inglo.giggle.document.domain.Reject;

import java.util.List;

public interface RejectRepository {

    Reject findTopByDocumentIdOrderByCreatedAtDescOrElseNull(Long id);

    List<Reject> findAllByDocumentId(Long id);

    void save(Reject reject);

    void deleteAll(List<Reject> rejects);
}
