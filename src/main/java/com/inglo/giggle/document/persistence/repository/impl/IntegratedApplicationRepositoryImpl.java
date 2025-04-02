package com.inglo.giggle.document.persistence.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.domain.IntegratedApplication;
import com.inglo.giggle.document.persistence.entity.IntegratedApplicationEntity;
import com.inglo.giggle.document.persistence.mapper.IntegratedApplicationMapper;
import com.inglo.giggle.document.persistence.repository.IntegratedApplicationRepository;
import com.inglo.giggle.document.persistence.repository.mysql.IntegratedApplicationJpaRepository;
import com.inglo.giggle.school.domain.School;
import com.inglo.giggle.school.persistence.entity.SchoolEntity;
import com.inglo.giggle.school.persistence.repository.mysql.SchoolJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class IntegratedApplicationRepositoryImpl implements IntegratedApplicationRepository {

    private final IntegratedApplicationJpaRepository integratedApplicationJpaRepository;
    private final SchoolJpaRepository schoolJpaRepository;

    @Override
    public IntegratedApplication findByIdOrElseThrow(Long id) {
        return IntegratedApplicationMapper.toDomain(integratedApplicationJpaRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_INTEGRATED_APPLICATION)));
    }

    @Override
    public IntegratedApplication findByUserOwnerJobPostingIdOrElseNull(Long userOwnerJobPostingId) {
        return IntegratedApplicationMapper.toDomain(integratedApplicationJpaRepository.findByUserOwnerJobPostingEntityId(userOwnerJobPostingId)
                .orElse(null));
    }

    @Override
    public IntegratedApplication findWithSchoolByIdOrElseThrow(Long documentId) {
        return IntegratedApplicationMapper.toDomain(integratedApplicationJpaRepository.findWithSchoolById(documentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_INTEGRATED_APPLICATION)));
    }

    @Override
    public IntegratedApplication save(IntegratedApplication integratedApplication) {
        IntegratedApplicationEntity entity = integratedApplicationJpaRepository.save(IntegratedApplicationMapper.toEntity(integratedApplication));
        Optional<SchoolEntity> schoolEntity = schoolJpaRepository.findById(integratedApplication.getSchoolId());
        if (schoolEntity.isPresent()) {
            entity.fetchSchool(schoolEntity.get());
            integratedApplicationJpaRepository.save(entity);
        }
        return IntegratedApplicationMapper.toDomain(entity);
    }
}
