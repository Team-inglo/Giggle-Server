package com.inglo.giggle.resume.persistence.repository.mysql;

import com.inglo.giggle.resume.persistence.entity.ResumeEntity;
import com.inglo.giggle.resume.persistence.entity.WorkExperienceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkExperienceJpaRepository extends JpaRepository<WorkExperienceEntity, Long>{

    List<WorkExperienceEntity> findAllByResumeEntity(ResumeEntity resumeEntity);
}
