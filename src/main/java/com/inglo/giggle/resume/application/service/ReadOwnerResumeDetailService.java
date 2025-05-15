package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.service.UserOwnerJobPostingService;
import com.inglo.giggle.posting.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.resume.application.dto.response.ReadOwnerResumeDetailResponseDtoV1;
import com.inglo.giggle.resume.application.dto.response.ReadOwnerResumeDetailResponseDtoV2;
import com.inglo.giggle.resume.application.usecase.ReadOwnerResumeDetailUseCase;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.WorkExperience;
import com.inglo.giggle.resume.repository.EducationRepository;
import com.inglo.giggle.resume.repository.LanguageSkillRepository;
import com.inglo.giggle.resume.repository.ResumeRepository;
import com.inglo.giggle.resume.repository.WorkExperienceRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final WorkExperienceRepository workExperienceRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadOwnerResumeDetailResponseDtoV1 execute(UUID accountId, Long userOwnerJobPostingId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        accountService.checkOwnerValidation(account);

        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithUserById(userOwnerJobPostingId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // UserOwnerJobPosting 유효성 체크
        userOwnerJobPostingService.checkOwnerUserOwnerJobPostingValidation(userOwnerJobPosting, accountId);

        // Resume 조회
        Resume resume = resumeRepository.findWithEducationsByAccountIdOrElseThrow(userOwnerJobPosting.getUser().getId());

        // education 조회
        List<Education> educations = educationRepository.findAllByResume(resume);

        // LanguageSkill 조회
        LanguageSkill languageSkill = languageSkillRepository.findByResumeOrElseThrow(resume);

        // WorkExperience 조회
        List<WorkExperience> workExperiences = workExperienceRepository.findAllByResume(resume);

        return ReadOwnerResumeDetailResponseDtoV1.of(resume, workExperiences, educations, languageSkill, userOwnerJobPosting.getUser());
    }

    @Override
    @Transactional(readOnly = true)
    public ReadOwnerResumeDetailResponseDtoV2 executeV2(UUID accountId, Long userOwnerJobPostingId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        accountService.checkOwnerValidation(account);

        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithUserById(userOwnerJobPostingId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // UserOwnerJobPosting 유효성 체크
        userOwnerJobPostingService.checkOwnerUserOwnerJobPostingValidation(userOwnerJobPosting, accountId);

        // Resume 조회
        Resume resume = resumeRepository.findWithEducationsByAccountIdOrElseThrow(userOwnerJobPosting.getUser().getId());

        // education 조회
        List<Education> educations = educationRepository.findAllByResume(resume);

        // LanguageSkill 조회
        LanguageSkill languageSkill = languageSkillRepository.findByResumeOrElseThrow(resume);

        // WorkExperience 조회
        List<WorkExperience> workExperiences = workExperienceRepository.findAllByResume(resume);

        return ReadOwnerResumeDetailResponseDtoV2.of(resume, workExperiences, educations, languageSkill, userOwnerJobPosting.getUser());
    }

}
