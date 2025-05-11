package com.inglo.giggle.resume.adapter.out.persistence.repository.mysql;

import com.inglo.giggle.resume.adapter.out.persistence.entity.WorkExperienceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WorkExperienceJpaRepository extends JpaRepository<WorkExperienceEntity, Long>{

    List<WorkExperienceEntity> findAllByResumeEntityAccountId(UUID resumeId);
}
