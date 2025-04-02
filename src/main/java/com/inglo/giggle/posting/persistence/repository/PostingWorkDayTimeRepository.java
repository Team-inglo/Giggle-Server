package com.inglo.giggle.posting.persistence.repository;

import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.PostingWorkDayTime;

import java.util.List;

public interface PostingWorkDayTimeRepository {

    PostingWorkDayTime findByIdOrElseThrow(Long id);

    List<PostingWorkDayTime> findAllByJobPosting(JobPosting jobPosting);

    PostingWorkDayTime save(PostingWorkDayTime postingWorkDayTime);

    void delete(PostingWorkDayTime postingWorkDayTime);
}
