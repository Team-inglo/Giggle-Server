package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.dto.RouteResponseDto;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.core.utility.OSRMUtil;
import com.inglo.giggle.core.utility.RestClientUtil;
import com.inglo.giggle.posting.application.dto.response.ReadUserJobPostingBriefResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadUserJobPostingBriefUseCase;
import com.inglo.giggle.posting.domain.JobPostAggregate;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.service.JobPostAggregateService;
import com.inglo.giggle.posting.domain.service.JobPostingService;
import com.inglo.giggle.posting.repository.JobPostingRepository;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.ResumeAggregate;
import com.inglo.giggle.resume.domain.service.EducationService;
import com.inglo.giggle.resume.domain.service.ResumeAggregateService;
import com.inglo.giggle.resume.repository.EducationRepository;
import com.inglo.giggle.resume.repository.ResumeRepository;
import com.inglo.giggle.school.domain.School;
import com.inglo.giggle.school.repository.SchoolRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReadUserJobPostingBriefService implements ReadUserJobPostingBriefUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    private final JobPostingRepository jobPostingRepository;
    private final ResumeRepository resumeRepository;
    private final EducationRepository educationRepository;
    private final JobPostingService jobPostingService;
    private final EducationService educationService;
    private final ResumeAggregateService resumeAggregateService;
    private final JobPostAggregateService jobPostAggregateService;
    private final SchoolRepository schoolRepository;
    private final RestClientUtil restClientUtil;
    private final OSRMUtil osrmUtil;

    private final static String METROPOLITAN_DURATION = "7200";
    private final static String NON_METROPOLITAN_DURATION = "5400";

    @Override
    @Transactional(readOnly = true)
    public ReadUserJobPostingBriefResponseDto execute(UUID accountId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 검사
        accountService.checkUserValidation(account);

        // JobPosting 조회
        List<JobPosting> jobPostings = jobPostingRepository.findAll().stream()
                .filter(jobPosting -> {

                    // Resume 조회 및 null 체크
                    Optional<Resume> optionalResume = resumeRepository.findWithEducationsAndLanguageSkillByAccountIdOptional(accountId);
                    if (optionalResume.isEmpty()) {
                        return false;
                    }

                    Resume resume = optionalResume.get();
                    return isUserApplicableForJobPosting(resume, jobPosting);
                })
                .limit(2)
                .toList();

        // DTO 변환 후 반환
        return ReadUserJobPostingBriefResponseDto.fromEntities(
                jobPostings
        );
    }

    /* -------------------------------------------- */
    /* Private Methods ---------------------------- */
    /* -------------------------------------------- */
    private boolean isUserApplicableForJobPosting(Resume resume, JobPosting jobPosting) {
        try {
            EEducationLevel educationLevel = educationService.getEducationLevelByVisa(resume.getUser().getVisa());
            List<Education> educations = educationRepository.findEducationByAccountIdAndEducationLevel(resume.getUser().getId(), educationLevel);

            Education education = educationService.getLatestEducation(educations);

            boolean isApplicableFromEducation = validateUserIsApplicableFromEducationAndResume(resume, education, jobPosting);
            boolean isApplicableFromSchoolDistance = validateUserIsApplicableFromSchoolDistance(resume, jobPosting);

            return isApplicableFromEducation && isApplicableFromSchoolDistance;

        } catch (Exception e) {
            log.error("this is error: {}", e.getMessage());
            log.error(ErrorCode.INTERNAL_SERVER_ERROR_WITH_OSRM.getMessage());
            return false;
        }
    }


    private Boolean validateUserIsApplicableFromEducationAndResume(Resume resume, Education education, JobPosting jobPosting) {
        ResumeAggregate resumeAggregate = resumeAggregateService.createResumeAggregate(resume.getUser(), resume, education);

        Map<String, Integer> userWorkDayTimeMap = resumeAggregateService.calculateWorkHours(resumeAggregate);
        Map<String, Integer> jobPostingWorkDayTimeMap = jobPostingService.calculateWorkHours(jobPosting);

        JobPostAggregate jobPostAggregate = jobPostAggregateService.createJobPostAggregate(resume, jobPosting);
        return jobPostAggregateService.isUserApplicable(jobPostAggregate, userWorkDayTimeMap, jobPostingWorkDayTimeMap);
    }


    private Boolean validateUserIsApplicableFromSchoolDistance(Resume resume, JobPosting jobPosting) throws Exception {
        Optional<School> school = schoolRepository.findTopByUserIdOrderByGraduationDateDescOptional(resume.getUser().getId());
        if (school.isEmpty()) {
            return false;
        }

        School graduationSchool = school.get();
        Address schoolAddress = graduationSchool.getAddress();
        Address jobPostingAddress = jobPosting.getAddress();

        JSONObject jsonObject = restClientUtil.sendGetMethod(osrmUtil.createOSRMRequestUrl(
                schoolAddress.getLatitude(), schoolAddress.getLongitude(),
                jobPostingAddress.getLatitude(), jobPostingAddress.getLongitude()),
                null
        );

        RouteResponseDto routeResponseDto = osrmUtil.mapToRouteResponseDto(jsonObject);

        double maxDuration = graduationSchool.getIsMetropolitan() ? Double.parseDouble(METROPOLITAN_DURATION) : Double.parseDouble(NON_METROPOLITAN_DURATION);
        return routeResponseDto.routes().get(0).duration() < maxDuration;
    }
}