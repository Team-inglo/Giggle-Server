package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.port.in.query.ReadUserLanguageSummaryQuery;
import com.inglo.giggle.resume.application.port.in.result.ReadUserLanguageSummaryResult;
import com.inglo.giggle.resume.application.port.out.LoadResumePort;
import com.inglo.giggle.resume.domain.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserLanguageSummaryService implements ReadUserLanguageSummaryQuery {

    private final LoadResumePort loadResumePort;

    @Override
    @Transactional(readOnly = true)
    public ReadUserLanguageSummaryResult execute(UUID accountId) {

        // Resume 조회
        Resume resume = loadResumePort.loadResumeWithLanguageSkillAndAdditionalLanguageOrElseThrow(accountId);

        List<ReadUserLanguageSummaryResult.AdditionalLanguageDto> languageDetailDtos;

        // LanguageDetailDtos 생성
        if (!resume.getLanguageSkill().getAdditionalLanguages().isEmpty()) {
            languageDetailDtos = resume.getLanguageSkill().getAdditionalLanguages().stream()
                    .map(language -> ReadUserLanguageSummaryResult.AdditionalLanguageDto.of(
                            language.getId(),
                            language.getLanguageName(),
                            language.getLevel()
                    )).toList();
        } else {
            languageDetailDtos = null;
        }

        return ReadUserLanguageSummaryResult.of(resume.getLanguageSkill().getTopikLevel(),
                resume.getLanguageSkill().getSocialIntegrationLevel(),
                resume.getLanguageSkill().getSejongInstituteLevel(),
                languageDetailDtos
        );
    }

}
