package com.inglo.giggle.resume.application.port.out;

import com.inglo.giggle.resume.domain.Resume;

import java.util.UUID;

public interface LoadResumePort {

    Resume loadAllResumeOrElseThrow(UUID id);

    Resume loadResumeOrElseThrow(UUID id);

    Resume loadResumeWithEducationsOrElseThrow(UUID id);

    Resume loadResumeWithWorkExperiencesOrElseThrow(UUID id);

    Resume loadResumeWithLanguageSkillOrElseThrow(UUID id);

    Resume loadResumeWithLanguageSkillAndAdditionalLanguageOrElseThrow(UUID id);

    Resume loadResumeWithLanguageSkillAndEducationsOrElseThrow(UUID id);
}