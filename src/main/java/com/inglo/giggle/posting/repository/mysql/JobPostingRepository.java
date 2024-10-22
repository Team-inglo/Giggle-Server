package com.inglo.giggle.posting.repository.mysql;

import com.inglo.giggle.posting.domain.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long>{
}
