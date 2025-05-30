package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.resume.application.dto.response.ReadUserResumeDetailResponseDtoV1;
import com.inglo.giggle.resume.application.dto.response.ReadUserResumeDetailResponseDtoV2;
import com.inglo.giggle.resume.application.usecase.ReadUserResumeDetailUseCase;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.WorkExperience;
import com.inglo.giggle.resume.domain.WorkPreference;
import com.inglo.giggle.resume.domain.service.ResumeService;
import com.inglo.giggle.resume.repository.EducationRepository;
import com.inglo.giggle.resume.repository.LanguageSkillRepository;
import com.inglo.giggle.resume.repository.ResumeRepository;
import com.inglo.giggle.resume.repository.WorkExperienceRepository;
import com.inglo.giggle.resume.repository.WorkPreferenceRepository;
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
public class ReadUserResumeDetailService implements ReadUserResumeDetailUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final ResumeRepository resumeRepository;
    private final ResumeService resumeService;
    private final EducationRepository educationRepository;
    private final LanguageSkillRepository languageSkillRepository;
    private final WorkExperienceRepository workExperienceRepository;
    private final WorkPreferenceRepository workPreferenceRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadUserResumeDetailResponseDtoV1 execute(UUID accountId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        accountService.checkUserValidation(account);

        // User 형변환
        User user = (User) account;

        // Resume 조회
        Resume resume = resumeRepository.findWithWorkExperiencesAndLanguageSkillByAccountIdOrElseThrow(accountId);

        // Resume 유효성 체크
        resumeService.checkResumeValidation(resume, accountId);

        // education 조회
        List<Education> educations = educationRepository.findAllByResume(resume);

        // LanguageSkill 조회
        LanguageSkill languageSkill = languageSkillRepository.findByResumeOrElseThrow(resume);

        // WorkExperience 조회
        List<WorkExperience> workExperiences = workExperienceRepository.findAllByResume(resume);

        return ReadUserResumeDetailResponseDtoV1.of(resume, workExperiences, educations, languageSkill, user);
    }


    @Override
    @Transactional(readOnly = true)
    public ReadUserResumeDetailResponseDtoV2 executeV2(UUID accountId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        accountService.checkUserValidation(account);

        // User 형변환
        User user = (User) account;

        // Resume 조회
        Resume resume = resumeRepository.findWithWorkExperiencesAndLanguageSkillByAccountIdOrElseThrow(accountId);

        // Resume 유효성 체크
        resumeService.checkResumeValidation(resume, accountId);

        // education 조회
        List<Education> educations = educationRepository.findAllByResume(resume);

        // LanguageSkill 조회
        LanguageSkill languageSkill = languageSkillRepository.findByResumeOrElseThrow(resume);

        // WorkExperience 조회
        List<WorkExperience> workExperiences = workExperienceRepository.findAllByResume(resume);

        // WorkPreference 조회
        WorkPreference workPreference = workPreferenceRepository.findByResumeIdOrElseThrow(resume.getAccountId());

        return ReadUserResumeDetailResponseDtoV2.of(resume, workExperiences, educations, languageSkill, user, workPreference);
    }

}
