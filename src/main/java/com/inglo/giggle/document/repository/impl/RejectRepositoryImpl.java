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

    public Reject findTopByDocumentIdOrderByCreatedAtDescOrElseNull(Long id) {
        return rejectJpaRepository.findTopByDocumentIdOrderByCreatedAtDesc(id).orElse(null);
    }

    public List<Reject> findAllByDocumentId(Long id) {
        return rejectJpaRepository.findAllByDocumentId(id);
    }

    public void save(Reject reject) {
        rejectJpaRepository.save(reject);
    }

    public void delete(Reject reject) {
        rejectJpaRepository.delete(reject);
    }

    public void deleteById(Long id) {
        rejectJpaRepository.deleteById(id);
    }

    public void deleteAll(List<Reject> rejects) {
        rejectJpaRepository.deleteAll(rejects);
    }
}
