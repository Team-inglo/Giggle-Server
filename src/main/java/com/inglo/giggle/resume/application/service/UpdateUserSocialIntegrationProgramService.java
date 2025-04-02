package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.usecase.UpdateUserSocialIntegrationProgramUseCase;
import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.persistence.repository.LanguageSkillRepository;
import com.inglo.giggle.resume.presentation.dto.request.UpdateUserSocialIntegrationProgramReqeustDto;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserSocialIntegrationProgramService implements UpdateUserSocialIntegrationProgramUseCase {

    private final AccountRepository accountRepository;
    private final LanguageSkillRepository languageSkillRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, UpdateUserSocialIntegrationProgramReqeustDto requestDto) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        account.checkUserValidation();

        // LanguageSkill 조회
        LanguageSkill languageSkill = languageSkillRepository.findByIdOrElseThrow(accountId);

        // LanguageSkill 유효성 체크
        languageSkill.checkValidation(accountId);

        // SocialIntegrationProgram 업데이트
        languageSkill.updateSocialIntegrationLevel(requestDto.level());
        languageSkillRepository.save(languageSkill);
    }

}
