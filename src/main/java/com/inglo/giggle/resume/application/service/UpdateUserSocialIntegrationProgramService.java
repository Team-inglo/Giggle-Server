package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.application.dto.request.UpdateUserSocialIntegrationProgramReqeustDto;
import com.inglo.giggle.resume.application.usecase.UpdateUserSocialIntegrationProgramUseCase;
import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.domain.service.LanguageSkillService;
import com.inglo.giggle.resume.repository.mysql.LanguageSkillRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserSocialIntegrationProgramService implements UpdateUserSocialIntegrationProgramUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final LanguageSkillRepository languageSkillRepository;
    private final LanguageSkillService languageSkillService;

    @Override
    @Transactional
    public void execute(UUID accountId, UpdateUserSocialIntegrationProgramReqeustDto requestDto) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        accountService.checkUserValidation(account);

        // LanguageSkill 조회
        LanguageSkill languageSkill = languageSkillRepository.findById(accountId)
                .orElseThrow(()-> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // LanguageSkill 유효성 체크
        languageSkillService.checkLanguageSkillValidation(languageSkill, accountId);

        // LanguageSkill 업데이트
        languageSkill = languageSkillService.updateSocialIntegrationProgram(languageSkill, requestDto.level());
        languageSkillRepository.save(languageSkill);
    }

}
