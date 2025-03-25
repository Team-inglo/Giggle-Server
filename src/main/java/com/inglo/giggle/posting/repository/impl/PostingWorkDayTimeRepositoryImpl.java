package com.inglo.giggle.posting.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.PostingWorkDayTime;
import com.inglo.giggle.posting.repository.PostingWorkDayTimeRepository;
import com.inglo.giggle.posting.repository.mysql.PostingWorkDayTimeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostingWorkDayTimeRepositoryImpl implements PostingWorkDayTimeRepository {

    private final PostingWorkDayTimeJpaRepository postingWorkDayTimeJpaRepository;

    @Override
    public PostingWorkDayTime findByIdOrElseThrow(Long id) {
        return postingWorkDayTimeJpaRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_POSTING_WORK_DAY_TIME));
    }

    @Override
    public List<PostingWorkDayTime> findAllByJobPosting(JobPosting jobPosting) {
        return postingWorkDayTimeJpaRepository.findAllByJobPosting(jobPosting);
    }

    @Override
    public void save(PostingWorkDayTime postingWorkDayTime) {
        postingWorkDayTimeJpaRepository.save(postingWorkDayTime);
    }

    @Override
    public void saveAll(List<PostingWorkDayTime> postingWorkDayTimes) {
        postingWorkDayTimeJpaRepository.saveAll(postingWorkDayTimes);
    }

    @Override
    public void delete(PostingWorkDayTime postingWorkDayTime) {
        postingWorkDayTimeJpaRepository.delete(postingWorkDayTime);
    }
}
