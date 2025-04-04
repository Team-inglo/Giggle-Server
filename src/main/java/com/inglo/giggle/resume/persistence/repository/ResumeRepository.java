package com.inglo.giggle.resume.persistence.repository;

import com.inglo.giggle.resume.domain.Resume;

import java.util.Optional;
import java.util.UUID;

public interface ResumeRepository {

    Resume findByIdOrElseThrow(UUID id);

    Resume findWithEducationsAndLanguageSkillByAccountIdOrElseThrow(UUID accountId);

    Optional<Resume> findWithEducationsAndLanguageSkillByAccountIdOptional(UUID accountId);

    Resume findByAccountIdOrElseThrow(UUID accountId);

    Resume save(Resume resume);
}
