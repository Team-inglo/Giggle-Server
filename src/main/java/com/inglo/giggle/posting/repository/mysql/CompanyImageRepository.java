package com.inglo.giggle.posting.repository.mysql;

import com.inglo.giggle.posting.domain.CompanyImage;
import com.inglo.giggle.posting.domain.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyImageRepository extends JpaRepository<CompanyImage, Long>{
    void deleteAllByIdIn(List<Long> ids);

    List<CompanyImage> findAllByJobPostingId(Long jobPostingId);

    List<CompanyImage> findAllByJobPosting(JobPosting jobPosting);
}
