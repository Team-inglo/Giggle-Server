package com.inglo.giggle.resume.persistence.repository;

import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.domain.Resume;

import java.util.UUID;

public interface LanguageSkillRepository {

    LanguageSkill findByIdOrElseThrow(UUID id);

    LanguageSkill findByResumeOrElseThrow(Resume resume);

    LanguageSkill findByResumeIdOrElseThrow(UUID resumeId);

    LanguageSkill save(LanguageSkill languageSkill);
}
