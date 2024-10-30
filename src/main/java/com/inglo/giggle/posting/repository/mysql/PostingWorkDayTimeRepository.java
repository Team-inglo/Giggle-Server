package com.inglo.giggle.posting.repository.mysql;

import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.PostingWorkDayTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostingWorkDayTimeRepository extends JpaRepository<PostingWorkDayTime, Long> {

    List<PostingWorkDayTime> findAllByJobPosting(JobPosting jobPosting);
}
