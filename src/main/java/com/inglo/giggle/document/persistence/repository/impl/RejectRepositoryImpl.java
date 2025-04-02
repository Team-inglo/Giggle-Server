package com.inglo.giggle.document.persistence.repository.impl;

import com.inglo.giggle.document.domain.Reject;
import com.inglo.giggle.document.persistence.entity.RejectEntity;
import com.inglo.giggle.document.persistence.mapper.RejectMapper;
import com.inglo.giggle.document.persistence.repository.RejectRepository;
import com.inglo.giggle.document.persistence.repository.mysql.RejectJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RejectRepositoryImpl implements RejectRepository {

    private final RejectJpaRepository rejectJpaRepository;

    @Override
    public Reject findTopByDocumentIdOrderByCreatedAtDescOrElseNull(Long id) {
        return RejectMapper.toDomain(rejectJpaRepository.findTopByDocumentEntityIdOrderByCreatedAtDesc(id)
                .orElse(null));
    }

    @Override
    public List<Reject> findAllByDocumentId(Long id) {
        return RejectMapper.toDomains(rejectJpaRepository.findAllByDocumentEntityId(id));
    }

    @Override
    public Reject save(Reject reject) {
        RejectEntity entity = rejectJpaRepository.save(RejectMapper.toEntity(reject));
        return RejectMapper.toDomain(entity);
    }

    @Override
    public void deleteAll(List<Reject> rejects) {
        rejectJpaRepository.deleteAll(RejectMapper.toEntities(rejects));
    }
}
