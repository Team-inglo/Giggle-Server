package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.application.dto.request.UpdateUserAdditionalLanguageSkillRequestDto;
import com.inglo.giggle.resume.application.usecase.UpdateUserAdditionalLanguageSkillUseCase;
import com.inglo.giggle.resume.domain.AdditionalLanguage;
import com.inglo.giggle.resume.domain.service.AdditionalLanguageService;
import com.inglo.giggle.resume.repository.mysql.AdditionalLanguageRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserAdditionalLanguageSkillService implements UpdateUserAdditionalLanguageSkillUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final AdditionalLanguageRepository additionalLanguageRepository;
    private final AdditionalLanguageService additionalLanguageService;


    @Override
    @Transactional
    public void execute(UUID accountId,Long additionalLanguageSkillId, UpdateUserAdditionalLanguageSkillRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 계정 타입 유효성 체크
        accountService.checkUserValidation(account);

        // AdditionalLanguage 조회
        AdditionalLanguage additionalLanguage = additionalLanguageRepository.findWithLanguageSkillById(additionalLanguageSkillId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // AdditionalLanguage 유효성 체크
        additionalLanguageService.checkAdditionalLanguageValidation(additionalLanguage, accountId);

        // AdditionalLanguage 업데이트
        additionalLanguage = additionalLanguageService.updateAdditionalLanguage(
                additionalLanguage, requestDto.languageName(), requestDto.level()
        );
        additionalLanguageRepository.save(additionalLanguage);
    }

}
