package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.user.domain.User;
import com.inglo.giggle.core.domain.Address;
import com.inglo.giggle.core.dto.RouteResponseDto;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.core.utility.OSRMUtil;
import com.inglo.giggle.core.utility.RestClientUtil;
import com.inglo.giggle.posting.application.usecase.ReadUserJobPostingBriefUseCase;
import com.inglo.giggle.posting.domain.JobPostAggregate;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.service.JobPostAggregateService;
import com.inglo.giggle.posting.persistence.repository.JobPostingRepository;
import com.inglo.giggle.posting.presentation.dto.response.ReadUserJobPostingBriefResponseDto;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.service.EducationService;
import com.inglo.giggle.resume.domain.service.ResumeAggregateService;
import com.inglo.giggle.resume.application.port.out.LoadResumePort;
import com.inglo.giggle.school.domain.School;
import com.inglo.giggle.school.application.port.out.LoadSchoolPort;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
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

    private final LoadAccountPort loadAccountPort;

    private final JobPostingRepository jobPostingRepository;
    private final LoadResumePort loadResumePort;
    private final EducationRepository educationRepository;
    private final EducationService educationService;
    private final ResumeAggregateService resumeAggregateService;
    private final JobPostAggregateService jobPostAggregateService;
    private final LoadSchoolPort loadSchoolPort;
    private final RestClientUtil restClientUtil;
    private final OSRMUtil osrmUtil;

    private final static String METROPOLITAN_DURATION = "7200";
    private final static String NON_METROPOLITAN_DURATION = "5400";

    @Override
    @Transactional(readOnly = true)
    public ReadUserJobPostingBriefResponseDto execute(UUID accountId) {

        // Account 조회
        Account account = loadAccountPort.loadAccount(accountId);

        // 계정 타입 유효성 검사
        account.checkUserValidation();

        // JobPosting 조회
        List<JobPosting> jobPostings = jobPostingRepository.findAll().stream()
                .filter(jobPosting -> {

                    // Resume 조회 및 null 체크
                    Optional<Resume> optionalResume = loadResumePort.findWithEducationsAndLanguageSkillByAccountIdOptional(accountId);
                    if (optionalResume.isEmpty()) {
                        return false;
                    }

                    Resume resume = optionalResume.get();
                    return isUserApplicableForJobPosting(account, resume, jobPosting);
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
    private boolean isUserApplicableForJobPosting(Account account, Resume resume, JobPosting jobPosting) {
        try {
            User user = (User) account;
            EEducationLevel educationLevel = Education.getEducationLevelByVisa(user.getVisa());
            List<Education> educations = educationRepository.findEducationByAccountIdAndEducationLevel(user.getId(), educationLevel);

            Education educationEntity = educationService.getLatestEducation(educations);

            boolean isApplicableFromEducation = validateUserIsApplicableFromEducationAndResume(account, resume, educationEntity, jobPosting);
            boolean isApplicableFromSchoolDistance = validateUserIsApplicableFromSchoolDistance(account, jobPosting);

            return isApplicableFromEducation && isApplicableFromSchoolDistance;

        } catch (Exception e) {
            log.error("this is error: {}", e.getMessage());
            log.error(ErrorCode.INTERNAL_SERVER_ERROR_WITH_OSRM.getMessage());
            return false;
        }
    }


    private Boolean validateUserIsApplicableFromEducationAndResume(Account account, Resume resume, Education education, JobPosting jobPosting) {
        User user = (User) account;
        Resume resumeAggregate = Resume.builder()
                .user(user)
                .education(education)
                .resume(resume)
                .build();

        Map<String, Integer> userWorkDayTimeMap = resumeAggregateService.calculateWorkHours(resumeAggregate);
        Map<String, Integer> jobPostingWorkDayTimeMap = jobPosting.calculateWorkHours();

        JobPostAggregate jobPostAggregate = JobPostAggregate.builder()
                .jobPosting(jobPosting)
                .resume(resume)
                .build();

        return jobPostAggregateService.isUserApplicable(jobPostAggregate, userWorkDayTimeMap, jobPostingWorkDayTimeMap);
    }


    private Boolean validateUserIsApplicableFromSchoolDistance(Account account, JobPosting jobPosting) throws Exception {
        Optional<School> school = loadSchoolPort.loadSchoolOptional(account.getId());
        if (school.isEmpty()) {
            return false;
        }

        School graduationSchool = school.get();
        Address schoolAddressEntity = graduationSchool.getAddress();
        Address jobPostingAddress = jobPosting.getAddress();

        JSONObject jsonObject = restClientUtil.sendGetMethod(osrmUtil.createOSRMRequestUrl(
                schoolAddressEntity.getLatitude(), schoolAddressEntity.getLongitude(),
                jobPostingAddress.getLatitude(), jobPostingAddress.getLongitude()),
                null
        );

        RouteResponseDto routeResponseDto = osrmUtil.mapToRouteResponseDto(jsonObject);

        double maxDuration = graduationSchool.getIsMetropolitan() ? Double.parseDouble(METROPOLITAN_DURATION) : Double.parseDouble(NON_METROPOLITAN_DURATION);
        return routeResponseDto.routes().get(0).duration() < maxDuration;
    }
}