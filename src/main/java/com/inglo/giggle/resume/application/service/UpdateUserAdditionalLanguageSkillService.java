package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.usecase.UpdateUserAdditionalLanguageSkillUseCase;
import com.inglo.giggle.resume.domain.AdditionalLanguage;
import com.inglo.giggle.resume.persistence.repository.AdditionalLanguageRepository;
import com.inglo.giggle.resume.presentation.dto.request.UpdateUserAdditionalLanguageSkillRequestDto;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserAdditionalLanguageSkillService implements UpdateUserAdditionalLanguageSkillUseCase {

    private final AccountRepository accountRepository;
    private final AdditionalLanguageRepository additionalLanguageRepository;

    @Override
    @Transactional
    public void execute(UUID accountId,Long additionalLanguageSkillId, UpdateUserAdditionalLanguageSkillRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        account.checkUserValidation();

        // AdditionalLanguage 조회
        AdditionalLanguage additionalLanguage = additionalLanguageRepository.findWithLanguageSkillByIdOrElseThrow(additionalLanguageSkillId);

        // AdditionalLanguage 유효성 체크
        additionalLanguage.checkValidation(accountId);

        // AdditionalLanguage 업데이트
        additionalLanguage.updateSelf(
                requestDto.languageName(),
                requestDto.level()
        );
        additionalLanguageRepository.save(additionalLanguage);
    }

}
