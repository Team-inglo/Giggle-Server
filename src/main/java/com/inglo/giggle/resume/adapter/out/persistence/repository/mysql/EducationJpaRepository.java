package com.inglo.giggle.resume.adapter.out.persistence.repository.mysql;

import com.inglo.giggle.resume.adapter.out.persistence.entity.EducationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EducationJpaRepository extends JpaRepository<EducationEntity, Long> {

    List<EducationEntity> findAllByResumeEntityAccountId(UUID resumeId);

}
