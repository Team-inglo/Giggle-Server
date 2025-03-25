package com.inglo.giggle.posting.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.domain.CompanyImage;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.repository.CompanyImageRepository;
import com.inglo.giggle.posting.repository.mysql.CompanyImageJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CompanyImageRepositoryImpl implements CompanyImageRepository {

    private final CompanyImageJpaRepository companyImageJpaRepository;

    @Override
    public List<CompanyImage> findAllById(List<Long> ids) {
        return companyImageJpaRepository.findAllById(ids);
    }

    @Override
    public void deleteAllByIdIn(List<Long> ids) {
        companyImageJpaRepository.deleteAllByIdIn(ids);
    }

    @Override
    public List<CompanyImage> findAllByJobPostingId(Long jobPostingId) {
        return companyImageJpaRepository.findAllByJobPostingId(jobPostingId);
    }

    @Override
    public List<CompanyImage> findAllByJobPosting(JobPosting jobPosting) {
        return companyImageJpaRepository.findAllByJobPosting(jobPosting);
    }
}
