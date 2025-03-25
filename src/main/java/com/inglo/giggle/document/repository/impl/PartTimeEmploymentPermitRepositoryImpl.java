package com.inglo.giggle.document.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.repository.PartTimeEmploymentPermitRepository;
import com.inglo.giggle.document.repository.mysql.PartTimeEmploymentPermitJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PartTimeEmploymentPermitRepositoryImpl implements PartTimeEmploymentPermitRepository {

    private final PartTimeEmploymentPermitJpaRepository partTimeEmploymentPermitJpaRepository;

    @Override
    public PartTimeEmploymentPermit findByIdOrElseThrow(Long id) {
        return partTimeEmploymentPermitJpaRepository.findById(id).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_PART_TIME_EMPLOYMENT_PERMIT));
    }

    @Override
    public PartTimeEmploymentPermit findByUserOwnerJobPostingIdOrElseNull(Long userOwnerJobPostingId) {
        return partTimeEmploymentPermitJpaRepository.findByUserOwnerJobPostingId(userOwnerJobPostingId).orElse(null);
    }

    @Override
    public void save(PartTimeEmploymentPermit partTimeEmploymentPermit) {
        partTimeEmploymentPermitJpaRepository.save(partTimeEmploymentPermit);
    }

    @Override
    public void delete(PartTimeEmploymentPermit partTimeEmploymentPermit) {
        partTimeEmploymentPermitJpaRepository.delete(partTimeEmploymentPermit);
    }

    @Override
    public void deleteById(Long id) {
        partTimeEmploymentPermitJpaRepository.deleteById(id);
    }
}
