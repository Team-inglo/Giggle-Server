package com.inglo.giggle.resume.persistence.repository.mysql;

import com.inglo.giggle.resume.persistence.entity.ResumeEntity;
import com.inglo.giggle.resume.persistence.entity.WorkExperienceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WorkExperienceJpaRepository extends JpaRepository<WorkExperienceEntity, Long>{

    List<WorkExperienceEntity> findAllByResumeId(UUID resumeId);
}
