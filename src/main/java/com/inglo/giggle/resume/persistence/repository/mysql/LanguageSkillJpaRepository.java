package com.inglo.giggle.resume.persistence.repository.mysql;

import com.inglo.giggle.resume.persistence.entity.LanguageSkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LanguageSkillJpaRepository extends JpaRepository<LanguageSkillEntity, UUID>{

    Optional<LanguageSkillEntity> findByResumeId(UUID resumeId);
}
