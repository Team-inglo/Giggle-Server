package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.usecase.ReadUserLanguageSummaryUseCase;
import com.inglo.giggle.resume.domain.AdditionalLanguage;
import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.ResumeAggregate;
import com.inglo.giggle.resume.persistence.repository.AdditionalLanguageRepository;
import com.inglo.giggle.resume.persistence.repository.LanguageSkillRepository;
import com.inglo.giggle.resume.persistence.repository.ResumeRepository;
import com.inglo.giggle.resume.presentation.dto.response.ReadUserLanguageSummaryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserLanguageSummaryService implements ReadUserLanguageSummaryUseCase {

    private final ResumeRepository resumeRepository;
    private final LanguageSkillRepository languageSkillRepository;
    private final AdditionalLanguageRepository additionalLanguageRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadUserLanguageSummaryResponseDto execute(UUID accountId) {

        // ResumeAggregate 생성
        ResumeAggregate resumeAggregate = getResumeAggregate(accountId);

        return ReadUserLanguageSummaryResponseDto.from(resumeAggregate);
    }

    /* ---------------------------------------------------------------------------------------------------------------*
     * -------                                       private method                                            -------*
     * -------------------------------------------------------------------------------------------------------------- */
    private ResumeAggregate getResumeAggregate(UUID resumeId) {
        // Resume 조회
        Resume resume = resumeRepository.findByAccountIdOrElseThrow(resumeId);

        // LanguageSkill 조회
        LanguageSkill languageSkill = languageSkillRepository.findByResumeIdOrElseThrow(resume.getAccountId());

        // AdditionalLanguage 조회
        List<AdditionalLanguage> additionalLanguages = additionalLanguageRepository.findAllByLanguageSkillId(languageSkill.getResumeId());

        // ResumeAggregate 생성
        return ResumeAggregate.builder()
                .resume(resume)
                .languageSkill(languageSkill)
                .additionalLanguages(additionalLanguages)
                .build();
    }

}
