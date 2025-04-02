package com.inglo.giggle.document.persistence.repository;

import com.inglo.giggle.document.domain.Reject;
import com.inglo.giggle.document.persistence.entity.RejectEntity;

import java.util.List;

public interface RejectRepository {

    Reject findTopByDocumentIdOrderByCreatedAtDescOrElseNull(Long id);

    List<Reject> findAllByDocumentId(Long id);

    Reject save(Reject reject);

    void deleteAll(List<Reject> rejects);
}
