package com.inglo.giggle.resume.repository;

import com.inglo.giggle.core.type.EMajor;
import com.inglo.giggle.core.type.ENationality;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.posting.domain.type.EJobCategory;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.type.EKorean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ResumeRepository {

    Resume findByIdOrElseThrow(UUID id);

    Resume findWithWorkExperiencesAndLanguageSkillByAccountIdOrElseThrow(UUID id);

    Resume findWithLanguageSkillByAccountIdOrElseThrow(UUID id);

    Resume findWithEducationsAndLanguageSkillByAccountIdOrElseThrow(UUID accountId);

    Optional<Resume> findWithEducationsAndLanguageSkillByAccountIdOptional(UUID accountId);

    Resume findWithEducationsByAccountIdOrElseThrow(UUID accountId);

    Resume findWithEducationsAndBookmarksByAccountIdOrElseThrow(UUID accountId);

    void save(Resume resume);

    Page<Resume> findResumesOrderByBookMarks(
            UUID accountId,
            List<EVisa> visa,
            List<EKorean> korean,
            List<EMajor> major,
            List<ENationality> nationality,
            List<EJobCategory> industry,
            Pageable pageable
    );

    Page<Resume> findResumesOrderByCreatedAt(
            UUID accountId,
            List<EVisa> visa,
            List<EKorean> korean,
            List<EMajor> major,
            List<ENationality> nationality,
            List<EJobCategory> industry,
            Pageable pageable
    );

    Page<Resume> findBookmarkedResumes(UUID accountId, Pageable pageable);
}
