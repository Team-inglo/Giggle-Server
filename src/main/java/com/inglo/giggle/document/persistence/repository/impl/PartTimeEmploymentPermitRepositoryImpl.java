package com.inglo.giggle.document.persistence.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.persistence.entity.PartTimeEmploymentPermitEntity;
import com.inglo.giggle.document.persistence.mapper.PartTimeEmploymentPermitMapper;
import com.inglo.giggle.document.persistence.repository.PartTimeEmploymentPermitRepository;
import com.inglo.giggle.document.persistence.repository.mysql.PartTimeEmploymentPermitJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PartTimeEmploymentPermitRepositoryImpl implements PartTimeEmploymentPermitRepository {

    private final PartTimeEmploymentPermitJpaRepository partTimeEmploymentPermitJpaRepository;

    @Override
    public PartTimeEmploymentPermit findByIdOrElseThrow(Long id) {
        return PartTimeEmploymentPermitMapper.toDomain(partTimeEmploymentPermitJpaRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_PART_TIME_EMPLOYMENT_PERMIT)));
    }

    @Override
    public PartTimeEmploymentPermit findByUserOwnerJobPostingIdOrElseNull(Long userOwnerJobPostingId) {
        return PartTimeEmploymentPermitMapper.toDomain(partTimeEmploymentPermitJpaRepository.findByUserOwnerJobPostingEntityId(userOwnerJobPostingId).
                orElse(null));
    }

    @Override
    public PartTimeEmploymentPermit save(PartTimeEmploymentPermit partTimeEmploymentPermit) {
        PartTimeEmploymentPermitEntity entity = partTimeEmploymentPermitJpaRepository.save(PartTimeEmploymentPermitMapper.toEntity(partTimeEmploymentPermit));
        return PartTimeEmploymentPermitMapper.toDomain(entity);
    }
}
