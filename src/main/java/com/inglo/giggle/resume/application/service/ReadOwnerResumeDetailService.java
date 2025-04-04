package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.UserOwnerJobPostingAggregate;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.resume.application.vo.EducationWithSchoolDto;
import com.inglo.giggle.resume.application.usecase.ReadOwnerResumeDetailUseCase;
import com.inglo.giggle.resume.domain.AdditionalLanguage;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.ResumeAggregate;
import com.inglo.giggle.resume.domain.WorkExperience;
import com.inglo.giggle.resume.persistence.repository.AdditionalLanguageRepository;
import com.inglo.giggle.resume.persistence.repository.EducationRepository;
import com.inglo.giggle.resume.persistence.repository.LanguageSkillRepository;
import com.inglo.giggle.resume.persistence.repository.ResumeRepository;
import com.inglo.giggle.resume.persistence.repository.WorkExperienceRepository;
import com.inglo.giggle.resume.presentation.dto.response.ReadOwnerResumeDetailResponseDto;
import com.inglo.giggle.school.domain.School;
import com.inglo.giggle.school.persistence.repository.SchoolRepository;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReadOwnerResumeDetailService implements ReadOwnerResumeDetailUseCase {

    private final AccountRepository accountRepository;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final ResumeRepository resumeRepository;
    private final EducationRepository educationRepository;
    private final LanguageSkillRepository languageSkillRepository;
    private final WorkExperienceRepository workExperienceRepository;
    private final SchoolRepository schoolRepository;
    private final AdditionalLanguageRepository additionalLanguageRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadOwnerResumeDetailResponseDto execute(UUID accountId, Long userOwnerJobPostingId) {

        // UserOwnerJobPostingAggregate 생성
        UserOwnerJobPostingAggregate userOwnerJobPostingAggregate = getUserOwnerJobPostingAggregate(accountId, userOwnerJobPostingId);

        // UserOwnerJobPosting 유효성 체크
        userOwnerJobPostingAggregate.checkOwnerUserOwnerJobPostingValidation(accountId);

        // ResumeAggregate 생성
        ResumeAggregate resumeAggregate = getResumeAggregate(userOwnerJobPostingAggregate.getUserOwnerJobPosting().getUserId());

        // EducationWithSchoolDtos 생성
        List<EducationWithSchoolDto> educationWithSchoolDtos = getEducationWithSchoolDtos(resumeAggregate.getEducations());

        return ReadOwnerResumeDetailResponseDto.of(userOwnerJobPostingAggregate, resumeAggregate, educationWithSchoolDtos);
    }

    /* ---------------------------------------------------------------------------------------------------------------*
     * -------                                       private method                                            -------*
     * -------------------------------------------------------------------------------------------------------------- */
    private UserOwnerJobPostingAggregate getUserOwnerJobPostingAggregate(UUID accountId, Long userOwnerJobPostingId) {

        // Account 조회
        Account ownerAccount = accountRepository.findByIdOrElseThrow(accountId);

        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findByIdOrElseThrow(userOwnerJobPostingId);

        // User 조회
        Account userAccount = accountRepository.findByIdOrElseThrow(userOwnerJobPosting.getUserId());

        // UserOwnerJobPostingAggregate 생성
        return UserOwnerJobPostingAggregate.builder()
                .userOwnerJobPosting(userOwnerJobPosting)
                .user((User) userAccount)
                .owner((Owner) ownerAccount)
                .build();
    }

    private ResumeAggregate getResumeAggregate(UUID resumeId) {
        // Resume 조회
        Resume resume = resumeRepository.findByAccountIdOrElseThrow(resumeId);

        // education 조회
        List<Education> educations = educationRepository.findAllByResumeId(resume.getAccountId());

        // LanguageSkill 조회
        LanguageSkill languageSkill = languageSkillRepository.findByResumeIdOrElseThrow(resume.getAccountId());

        // WorkExperience 조회
        List<WorkExperience> workExperiences = workExperienceRepository.findAllByResumeId(resume.getAccountId());

        // AdditionalLanguage 조회
        List<AdditionalLanguage> additionalLanguages = additionalLanguageRepository.findAllByLanguageSkillId(languageSkill.getResumeId());

        // ResumeAggregate 생성
        return ResumeAggregate.builder()
                .resume(resume)
                .educations(educations)
                .languageSkill(languageSkill)
                .workExperiences(workExperiences)
                .additionalLanguages(additionalLanguages)
                .build();
    }

    private List<EducationWithSchoolDto> getEducationWithSchoolDtos(List<Education> educations) {
        // School 조회
        List<School> schools = schoolRepository.findAllByIds(educations.stream()
                .map(Education::getSchoolId)
                .toList());

        // School Map 생성
        Map<Long, School> schoolMap = schools.stream()
                .collect(Collectors.toMap(School::getId, s -> s));

        // EducationWithSchoolDto 생성
        return educations.stream()
                .map(education -> new EducationWithSchoolDto(education, schoolMap.get(education.getSchoolId())))
                .toList();
    }
}
