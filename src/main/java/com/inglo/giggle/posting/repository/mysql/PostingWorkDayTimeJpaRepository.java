package com.inglo.giggle.posting.repository.mysql;

import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.PostingWorkDayTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostingWorkDayTimeJpaRepository extends JpaRepository<PostingWorkDayTime, Long> {

    List<PostingWorkDayTime> findAllByJobPosting(JobPosting jobPosting);
}
