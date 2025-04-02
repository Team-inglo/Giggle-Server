package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.usecase.ReadUserLanguageSummaryUseCase;
import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.persistence.repository.LanguageSkillRepository;
import com.inglo.giggle.resume.presentation.dto.response.ReadUserLanguageSummaryResponseDto;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserLanguageSummaryService implements ReadUserLanguageSummaryUseCase {

    private final AccountRepository accountRepository;
    private final LanguageSkillRepository languageSkillRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadUserLanguageSummaryResponseDto execute(UUID accountId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        account.checkUserValidation();

        // LanguageSkill 조회
        LanguageSkill languageSkill = languageSkillRepository.findByResumeIdOrElseThrow(accountId);

        // LanguageSkill 유효성 체크
        languageSkill.checkValidation(accountId);

        return ReadUserLanguageSummaryResponseDto.from(languageSkill);
    }

}
