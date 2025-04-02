package com.inglo.giggle.posting.persistence.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.PostingWorkDayTime;
import com.inglo.giggle.posting.persistence.entity.PostingWorkDayTimeEntity;
import com.inglo.giggle.posting.persistence.mapper.JobPostingMapper;
import com.inglo.giggle.posting.persistence.mapper.PostingWorkDayTimeMapper;
import com.inglo.giggle.posting.persistence.repository.PostingWorkDayTimeRepository;
import com.inglo.giggle.posting.persistence.repository.mysql.PostingWorkDayTimeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostingWorkDayTimeRepositoryImpl implements PostingWorkDayTimeRepository {

    private final PostingWorkDayTimeJpaRepository postingWorkDayTimeJpaRepository;

    @Override
    public PostingWorkDayTime findByIdOrElseThrow(Long id) {
        return PostingWorkDayTimeMapper.toDomain(postingWorkDayTimeJpaRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_POSTING_WORK_DAY_TIME)));
    }

    @Override
    public List<PostingWorkDayTime> findAllByJobPosting(JobPosting jobPosting) {
        return PostingWorkDayTimeMapper.toDomains(postingWorkDayTimeJpaRepository.findAllByJobPostingEntity(JobPostingMapper.toEntity(jobPosting)));
    }

    @Override
    public PostingWorkDayTime save(PostingWorkDayTime postingWorkDayTime) {
        PostingWorkDayTimeEntity entity = postingWorkDayTimeJpaRepository.save(PostingWorkDayTimeMapper.toEntity(postingWorkDayTime));
        return PostingWorkDayTimeMapper.toDomain(entity);
    }

    @Override
    public void delete(PostingWorkDayTime postingWorkDayTime) {
        postingWorkDayTimeJpaRepository.delete(PostingWorkDayTimeMapper.toEntity(postingWorkDayTime));
    }
}
