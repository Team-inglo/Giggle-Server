package com.inglo.giggle.posting.persistence.repository.mysql;

import com.inglo.giggle.posting.persistence.entity.JobPostingEntity;
import com.inglo.giggle.posting.persistence.entity.PostingWorkDayTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostingWorkDayTimeJpaRepository extends JpaRepository<PostingWorkDayTimeEntity, Long> {

    List<PostingWorkDayTimeEntity> findAllByJobPostingEntity(JobPostingEntity jobPostingEntity);
}
