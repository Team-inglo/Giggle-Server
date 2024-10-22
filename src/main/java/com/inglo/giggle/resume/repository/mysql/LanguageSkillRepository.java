package com.inglo.giggle.resume.repository.mysql;

import com.inglo.giggle.resume.domain.LanguageSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LanguageSkillRepository extends JpaRepository<LanguageSkill, UUID>{
}
