package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.dto.request.UpdateUserTopikReqeustDto;
import com.inglo.giggle.resume.application.usecase.UpdateUserTopikUseCase;
import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.domain.service.LanguageSkillService;
import com.inglo.giggle.resume.repository.LanguageSkillRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserTopikService implements UpdateUserTopikUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final LanguageSkillRepository languageSkillRepository;
    private final LanguageSkillService languageSkillService;

    @Override
    @Transactional
    public void execute(UUID accountId, UpdateUserTopikReqeustDto requestDto) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        accountService.checkUserValidation(account);

        // LanguageSkill 조회
        LanguageSkill languageSkill = languageSkillRepository.findByIdOrElseThrow(accountId);

        // LanguageSkill 유효성 체크
        languageSkillService.checkLanguageSkillValidation(languageSkill, accountId);

        // LanguageSkill 업데이트
        languageSkill = languageSkillService.updateTopik(
                languageSkill, requestDto.level()
        );
        languageSkillRepository.save(languageSkill);
    }

}
