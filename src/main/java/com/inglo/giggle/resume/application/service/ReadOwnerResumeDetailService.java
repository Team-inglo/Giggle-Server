package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.service.UserOwnerJobPostingService;
import com.inglo.giggle.posting.repository.mysql.UserOwnerJobPostingRepository;
import com.inglo.giggle.resume.application.dto.response.ReadOwnerResumeDetailResponseDto;
import com.inglo.giggle.resume.application.usecase.ReadOwnerResumeDetailUseCase;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.repository.mysql.EducationRepository;
import com.inglo.giggle.resume.repository.mysql.LanguageSkillRepository;
import com.inglo.giggle.resume.repository.mysql.ResumeRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadOwnerResumeDetailService implements ReadOwnerResumeDetailUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final UserOwnerJobPostingService userOwnerJobPostingService;
    private final ResumeRepository resumeRepository;
    private final EducationRepository educationRepository;
    private final LanguageSkillRepository languageSkillRepository;

    @Override
    public ReadOwnerResumeDetailResponseDto execute(UUID accountId, Long userOwnerJobPostingId) {

        // Account 조회
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 계정 타입 유효성 체크
        accountService.checkOwnerValidation(account);

        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithUserById(userOwnerJobPostingId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // UserOwnerJobPosting 유효성 체크
        userOwnerJobPostingService.checkOwnerUserOwnerJobPostingValidation(userOwnerJobPosting, accountId);

        // Resume 조회
        Resume resume = resumeRepository.findWithWorkExperiencesAndLanguageSkillByAccountId(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // education 조회
        List<Education> educations = educationRepository.findAllByResume(resume);

        // LanguageSkill 조회
        LanguageSkill languageSkill = languageSkillRepository.findByResume(resume)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        return ReadOwnerResumeDetailResponseDto.of(resume, educations, languageSkill, userOwnerJobPosting.getUser());
    }

}
