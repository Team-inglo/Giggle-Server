package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.dto.RouteResponseDto;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.core.utility.OSRMUtil;
import com.inglo.giggle.core.utility.RestClientUtil;
import com.inglo.giggle.posting.application.dto.response.ReadUserJobPostingValidationResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadUserJobPostingValidationUseCase;
import com.inglo.giggle.posting.domain.JobPostAggregate;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.service.JobPostAggregateService;
import com.inglo.giggle.posting.domain.service.JobPostingService;
import com.inglo.giggle.posting.repository.mysql.JobPostingRepository;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.ResumeAggregate;
import com.inglo.giggle.resume.domain.service.EducationService;
import com.inglo.giggle.resume.domain.service.ResumeAggregateService;
import com.inglo.giggle.resume.repository.mysql.EducationRepository;
import com.inglo.giggle.resume.repository.mysql.ResumeRepository;
import com.inglo.giggle.school.domain.School;
import com.inglo.giggle.school.repository.mysql.SchoolRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
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
public class ReadUserJobPostingValidationService implements ReadUserJobPostingValidationUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    private final JobPostAggregateService jobPostAggregateService;
    private final ResumeAggregateService resumeAggregateService;

    private final JobPostingService jobPostingService;
    private final EducationService educationService;

    private final EducationRepository educationRepository;
    private final SchoolRepository schoolRepository;
    private final JobPostingRepository jobPostingRepository;
    private final ResumeRepository resumeRepository;

    private final RestClientUtil restClientUtil;
    private final OSRMUtil osrmUtil;

    private final static String METROPOLITAN_DURATION = "7200";
    private final static String NON_METROPOLITAN_DURATION = "5400";

    @Override
    @Transactional(readOnly = true)
    public ReadUserJobPostingValidationResponseDto execute(UUID accountId, Long jobPostingId) throws Exception {

        // Account 조회
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 계정 타입 유효성 검사
        accountService.checkUserValidation(account);

        // 공고 조회
        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 이력서 정보 조회
        Resume resume = resumeRepository.findWithEducationsAndLanguageSkillByAccountId(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 유저의 비자에 맵핑되는 educationLevel 조회
        EEducationLevel educationLevel = educationService.getEducationLevelByVisa(resume.getUser().getVisa());

        // 유저의 educationLevel에 맞는 학력 정보 조회
        List<Education> educations = educationRepository.findEducationByAccountIdAndEducationLevel(resume.getUser().getId(), educationLevel);

        Education education = educationService.getLatestEducation(educations);

        // 유저 정보 검증 (거리, 학력, 언어 스킬 기반)
        Boolean isApplicableFromEducation = validateUserIsApplicableFromEducationAndResume(resume, education, jobPosting);
        Boolean isApplicableFromSchoolDistance = validateUserIsApplicableFromSchoolDistance(resume, jobPosting);

        log.info("isApplicableFromEducation: {}", isApplicableFromEducation);
        log.info("isApplicableFromSchoolDistance: {}", isApplicableFromSchoolDistance);

        return ReadUserJobPostingValidationResponseDto.builder()
                .isQualificationVerified(isApplicableFromEducation&&isApplicableFromSchoolDistance)
                .build();

    }

    /* -------------------------------------------- */
    /* Private Methods ---------------------------- */
    /* -------------------------------------------- */

    // 유저의 학력, 이력 정보를 통해 공고에 적합한지 검증하는 메서드
    private Boolean validateUserIsApplicableFromEducationAndResume(Resume resume, Education education, JobPosting jobPosting) {
        // ResumeAggregate 생성 및 반환
        ResumeAggregate resumeAggregate = resumeAggregateService.createResumeAggregate(resume.getUser(), resume, education);

        Map<String, Integer> userWorkDayTimeMap = resumeAggregateService.calculateWorkHours(resumeAggregate);
        Map<String, Integer> jobPostingWorkDayTimeMap = jobPostingService.calculateWorkHours(jobPosting);

        JobPostAggregate jobPostAggregate = jobPostAggregateService.createJobPostAggregate(resume, jobPosting);

        return jobPostAggregateService.isUserApplicable(jobPostAggregate, userWorkDayTimeMap, jobPostingWorkDayTimeMap);
    }

    // 유저의 학교의 거리로 부터 공고에 적합한지 검증하는 메서드
    private Boolean validateUserIsApplicableFromSchoolDistance(Resume resume, JobPosting jobPosting) throws Exception {
        Optional<School> school = schoolRepository.findTopByUserIdOrderByGraduationDateDesc(resume.getUser().getId());

        if(school.isEmpty()) {
            log.info("School is empty");
            return false;
        }

        School graduationSchool = school.get();
        Address schoolAddress = graduationSchool.getAddress();
        Address jobPostingAddress = jobPosting.getAddress();

        log.info("before OSRM Request");
        JSONObject jsonObject = restClientUtil.sendGetMethod(osrmUtil.createOSRMRequestUrl(
                schoolAddress.getLatitude(), schoolAddress.getLongitude(),
                jobPostingAddress.getLatitude(), jobPostingAddress.getLongitude()),
                null
        );
        log.info("OSRM Response: {}", jsonObject.toString());

        RouteResponseDto routeResponseDto = osrmUtil.mapToRouteResponseDto(jsonObject);
        log.info("RouteResponseDto: {}", routeResponseDto.toString());

        if(graduationSchool.getIsMetropolitan()){
            return routeResponseDto.routes().get(0).duration() < Double.parseDouble(METROPOLITAN_DURATION);
        }else{
            return routeResponseDto.routes().get(0).duration() < Double.parseDouble(NON_METROPOLITAN_DURATION);
        }
    }
}
