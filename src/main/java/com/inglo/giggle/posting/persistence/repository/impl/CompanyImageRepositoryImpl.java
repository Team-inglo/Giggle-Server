package com.inglo.giggle.posting.persistence.repository.impl;

import com.inglo.giggle.posting.domain.CompanyImage;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.persistence.entity.CompanyImageEntity;
import com.inglo.giggle.posting.persistence.entity.JobPostingEntity;
import com.inglo.giggle.posting.persistence.mapper.CompanyImageMapper;
import com.inglo.giggle.posting.persistence.mapper.JobPostingMapper;
import com.inglo.giggle.posting.persistence.repository.CompanyImageRepository;
import com.inglo.giggle.posting.persistence.repository.mysql.CompanyImageJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CompanyImageRepositoryImpl implements CompanyImageRepository {

    private final CompanyImageJpaRepository companyImageJpaRepository;

    @Override
    public List<CompanyImage> findAllById(List<Long> ids) {
        return CompanyImageMapper.toDomains(companyImageJpaRepository.findAllById(ids));
    }

    @Override
    public void deleteAllByIdIn(List<Long> ids) {
        companyImageJpaRepository.deleteAllByIdIn(ids);
    }

    @Override
    public List<CompanyImage> findAllByJobPostingId(Long jobPostingId) {
        return CompanyImageMapper.toDomains(companyImageJpaRepository.findAllByJobPostingEntityId(jobPostingId));
    }

    @Override
    public List<CompanyImage> findAllByJobPosting(JobPosting jobPosting) {
        return CompanyImageMapper.toDomains(companyImageJpaRepository.findAllByJobPostingEntity(JobPostingMapper.toEntity(jobPosting)));
    }
}
