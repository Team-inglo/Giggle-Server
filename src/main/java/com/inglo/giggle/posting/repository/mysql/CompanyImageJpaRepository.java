package com.inglo.giggle.posting.repository.mysql;

import com.inglo.giggle.posting.domain.CompanyImage;
import com.inglo.giggle.posting.domain.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyImageJpaRepository extends JpaRepository<CompanyImage, Long>{
    void deleteAllByIdIn(List<Long> ids);

    List<CompanyImage> findAllByJobPostingId(Long jobPostingId);

    List<CompanyImage> findAllByJobPosting(JobPosting jobPosting);
}
