package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.application.dto.request.CreateUserAdditionalLanguageSkillRequestDto;
import com.inglo.giggle.resume.application.usecase.CreateUserAdditionalLanguageSkillUseCase;
import com.inglo.giggle.resume.domain.AdditionalLanguage;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.service.AdditionalLanguageService;
import com.inglo.giggle.resume.repository.mysql.AdditionalLanguageRepository;
import com.inglo.giggle.resume.repository.mysql.ResumeRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserAdditionalLanguageSkillService implements CreateUserAdditionalLanguageSkillUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final AdditionalLanguageRepository additionalLanguageRepository;
    private final ResumeRepository resumeRepository;
    private final AdditionalLanguageService additionalLanguageService;

    @Override
    @Transactional
    public void execute(UUID accountId, CreateUserAdditionalLanguageSkillRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 계정 타입 유효성 체크
        accountService.checkUserValidation(account);

        // Resume 조회
        Resume resume = resumeRepository.findWithLanguageSkillByAccountId(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // LanguageSkill 가져옴
        List<AdditionalLanguage> additionalLanguages = resume.getLanguageSkill().getAdditionalLanguages();

        // 이미 존재하는 언어인지 체크
        additionalLanguageService.checkIsExistAdditionalLanguage(additionalLanguages, requestDto.languageName());

        // AdditionalLanguage 생성
        AdditionalLanguage additionalLanguage = additionalLanguageService.createAdditionalLanguage(
                requestDto.languageName(),
                requestDto.level(),
                resume.getLanguageSkill()
        );
        additionalLanguageRepository.save(additionalLanguage);
    }

}
