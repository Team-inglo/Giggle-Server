package com.inglo.giggle.posting.repository;

import com.inglo.giggle.posting.domain.CompanyImage;
import com.inglo.giggle.posting.domain.JobPosting;

import java.util.List;

public interface CompanyImageRepository {

    List<CompanyImage> findAllById(List<Long> ids);

    void deleteAllByIdIn(List<Long> ids);

    List<CompanyImage> findAllByJobPostingId(Long jobPostingId);

    List<CompanyImage> findAllByJobPosting(JobPosting jobPosting);
}
