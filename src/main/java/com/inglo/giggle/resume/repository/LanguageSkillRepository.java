package com.inglo.giggle.resume.repository;

import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.domain.Resume;

import java.util.Optional;
import java.util.UUID;

public interface LanguageSkillRepository {

    LanguageSkill findByIdOrElseThrow(UUID id);

    LanguageSkill findByResumeOrElseThrow(Resume resume);

    LanguageSkill findByResumeIdOrElseThrow(UUID resumeId);

    void save(LanguageSkill languageSkill);

    void delete(LanguageSkill languageSkill);
}
