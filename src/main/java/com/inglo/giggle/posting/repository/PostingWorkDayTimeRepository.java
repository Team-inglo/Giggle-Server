package com.inglo.giggle.posting.repository;

import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.PostingWorkDayTime;

import java.util.List;

public interface PostingWorkDayTimeRepository {

    PostingWorkDayTime findByIdOrElseThrow(Long id);

    List<PostingWorkDayTime> findAllByJobPosting(JobPosting jobPosting);

    void save(PostingWorkDayTime postingWorkDayTime);

    void saveAll(List<PostingWorkDayTime> postingWorkDayTimes);

    void delete(PostingWorkDayTime postingWorkDayTime);
}
