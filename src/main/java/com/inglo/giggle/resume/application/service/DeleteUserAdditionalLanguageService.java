package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.application.usecase.DeleteUserAdditionalLanguageUseCase;
import com.inglo.giggle.resume.domain.AdditionalLanguage;
import com.inglo.giggle.resume.domain.service.AdditionalLanguageService;
import com.inglo.giggle.resume.repository.mysql.AdditionalLanguageRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteUserAdditionalLanguageService implements DeleteUserAdditionalLanguageUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final AdditionalLanguageRepository additionalLanguageRepository;
    private final AdditionalLanguageService additionalLanguageService;

    @Override
    @Transactional
    public void execute(UUID accountId, Long additionalLanguageId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        accountService.checkUserValidation(account);

        // AdditionalLanguage 조회
        AdditionalLanguage additionalLanguage = additionalLanguageRepository.findWithLanguageSkillById(additionalLanguageId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // AdditionalLanguage 유효성 체크
        additionalLanguageService.checkAdditionalLanguageValidation(additionalLanguage, accountId);

        // AdditionalLanguage 삭제
        additionalLanguageRepository.delete(additionalLanguage);
    }

}
