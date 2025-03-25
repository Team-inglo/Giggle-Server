package com.inglo.giggle.document.repository.impl;

import com.inglo.giggle.document.domain.Reject;
import com.inglo.giggle.document.repository.RejectRepository;
import com.inglo.giggle.document.repository.mysql.RejectJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RejectRepositoryImpl implements RejectRepository {

    private final RejectJpaRepository rejectJpaRepository;

    @Override
    public Reject findTopByDocumentIdOrderByCreatedAtDescOrElseNull(Long id) {
        return rejectJpaRepository.findTopByDocumentIdOrderByCreatedAtDesc(id).orElse(null);
    }

    @Override
    public List<Reject> findAllByDocumentId(Long id) {
        return rejectJpaRepository.findAllByDocumentId(id);
    }

    @Override
    public void save(Reject reject) {
        rejectJpaRepository.save(reject);
    }

    @Override
    public void deleteAll(List<Reject> rejects) {
        rejectJpaRepository.deleteAll(rejects);
    }
}
