package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.usecase.DeleteUserAdditionalLanguageUseCase;
import com.inglo.giggle.resume.domain.AdditionalLanguage;
import com.inglo.giggle.resume.domain.service.AdditionalLanguageService;
import com.inglo.giggle.resume.persistence.repository.AdditionalLanguageRepository;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteUserAdditionalLanguageService implements DeleteUserAdditionalLanguageUseCase {

    private final AccountRepository accountRepository;
    private final AdditionalLanguageRepository additionalLanguageRepository;
    private final AdditionalLanguageService additionalLanguageService;

    @Override
    @Transactional
    public void execute(UUID accountId, Long additionalLanguageId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        account.checkUserValidation();

        // AdditionalLanguage 조회
        AdditionalLanguage additionalLanguage = additionalLanguageRepository.findWithLanguageSkillByIdOrElseThrow(additionalLanguageId);

        // AdditionalLanguage 유효성 체크
        additionalLanguage.checkValidation(accountId);

        // AdditionalLanguage 삭제
        additionalLanguageRepository.delete(additionalLanguage);
    }

}
