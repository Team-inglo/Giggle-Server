package com.inglo.giggle.resume.repository.mysql;

import com.inglo.giggle.resume.domain.LanguageSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface LanguageSkillRepository extends JpaRepository<LanguageSkill, UUID>{
}
