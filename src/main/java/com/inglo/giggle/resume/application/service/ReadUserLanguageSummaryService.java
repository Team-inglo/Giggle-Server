package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.application.dto.response.ReadUserLanguageSummaryResponseDto;
import com.inglo.giggle.resume.application.usecase.ReadUserLanguageSummaryUseCase;
import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.repository.mysql.LanguageSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserLanguageSummaryService implements ReadUserLanguageSummaryUseCase {
    private final LanguageSkillRepository languageSkillRepository;

    @Override
    public ReadUserLanguageSummaryResponseDto execute(UUID accountId) {
        LanguageSkill languageSkill = languageSkillRepository.findByResumeId(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        return ReadUserLanguageSummaryResponseDto.fromEntity(languageSkill);
    }
}
