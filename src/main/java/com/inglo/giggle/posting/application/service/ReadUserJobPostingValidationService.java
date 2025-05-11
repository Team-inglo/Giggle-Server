package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.core.utility.OSRMUtil;
import com.inglo.giggle.core.utility.RestClientUtil;
import com.inglo.giggle.posting.application.usecase.ReadUserJobPostingValidationUseCase;
import com.inglo.giggle.posting.domain.service.JobPostAggregateService;
import com.inglo.giggle.posting.persistence.repository.JobPostingRepository;
import com.inglo.giggle.posting.presentation.dto.response.ReadUserJobPostingValidationResponseDto;
import com.inglo.giggle.resume.domain.service.ResumeAggregateService;
import com.inglo.giggle.resume.application.port.out.LoadResumePort;
import com.inglo.giggle.school.application.port.out.LoadSchoolPort;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReadUserJobPostingValidationService implements ReadUserJobPostingValidationUseCase {

    private final JobPostAggregateService jobPostAggregateService;
    private final ResumeAggregateService resumeAggregateService;

    private final LoadAccountPort loadAccountPort;
    private final LoadSchoolPort loadSchoolPort;
    private final JobPostingRepository jobPostingRepository;
    private final LoadResumePort loadResumePort;

    private final RestClientUtil restClientUtil;
    private final OSRMUtil osrmUtil;

    private final static String METROPOLITAN_DURATION = "7200";
    private final static String NON_METROPOLITAN_DURATION = "5400";

    @Override
    @Transactional(readOnly = true)
    public ReadUserJobPostingValidationResponseDto execute(UUID accountId, Long jobPostingId) throws Exception {

        return ReadUserJobPostingValidationResponseDto.of(true);

//        // Account 조회
//        Account user = accountRepository.loadUser(accountId);
//
//        // 계정 타입 유효성 검사
//        user.checkUserValidation();
//
//        // 공고 조회
//        JobPosting jobPosting = jobPostingRepository.loadUser(jobPostingId);
//
//        // 이력서 정보 조회
//        Resume resume = resumeRepository.findWithEducationsAndLanguageSkillByAccountIdOrElseThrow(accountId);
//
//        // 유저의 비자에 맵핑되는 educationLevel 조회
//        EEducationLevel educationLevel = educationService.getEducationLevelByVisa(resume.getUser().getVisa());
//
//        // 유저의 educationLevel에 맞는 학력 정보 조회
//        List<Education> educations = educationRepository.findEducationByAccountIdAndEducationLevel(resume.getUser().getId(), educationLevel);
//
//        Education education = educationService.getLatestEducation(educations);
//
//        // 유저 정보 검증 (거리, 학력, 언어 스킬 기반)
//        Boolean isApplicableFromEducation = validateUserIsApplicableFromEducationAndResume(resume, education, jobPosting);
//        Boolean isApplicableFromSchoolDistance = validateUserIsApplicableFromSchoolDistance(resume, jobPosting);
//
//        log.info("isApplicableFromEducation: {}", isApplicableFromEducation);
//        log.info("isApplicableFromSchoolDistance: {}", isApplicableFromSchoolDistance);
//
//        return ReadUserJobPostingValidationResponseDto.builder()
//                .isQualificationVerified(isApplicableFromEducation&&isApplicableFromSchoolDistance)
//                .build();

    }


    /* -------------------------------------------- */
    /* Private Methods ---------------------------- */
    /* -------------------------------------------- */

    // 유저의 학력, 이력 정보를 통해 공고에 적합한지 검증하는 메서드
//    private Boolean validateUserIsApplicableFromEducationAndResume(Resume resume, EducationEntity educationEntity, JobPosting jobPosting) {
//        // ResumeAggregate 생성 및 반환
//        ResumeAggregate resumeAggregate = ResumeAggregate.builder()
//                .user(resume)
//                .resume(resumeEntity)
//                .education(educationEntity)
//                .build();
//
//        Map<String, Integer> userWorkDayTimeMap = resumeAggregateService.calculateWorkHours(resumeAggregate);
//        Map<String, Integer> jobPostingWorkDayTimeMap = jobPosting.calculateWorkHours();
//
//        JobPostAggregate jobPostAggregate = jobPostAggregateService.createJobPostAggregate(resumeEntity, jobPosting);
//
//        return jobPostAggregateService.isUserApplicable(jobPostAggregate, userWorkDayTimeMap, jobPostingWorkDayTimeMap);
//    }
//
//    // 유저의 학교의 거리로 부터 공고에 적합한지 검증하는 메서드
//    private Boolean validateUserIsApplicableFromSchoolDistance(ResumeEntity resumeEntity, JobPosting jobPosting) throws Exception {
//        Optional<School> school = schoolRepository.findTopByUserIdOrderByGraduationDateDescOptional(resumeEntity.getUserEntity().getId());
//
//        if(school.isEmpty()) {
//            log.info("School is empty");
//            return false;
//        }
//
//        School graduationSchool = school.get();
//        Address schoolAddress = graduationSchool.getAddress();
//        Address jobPostingAddress = jobPosting.getAddress();
//
//        log.info("before OSRM Request");
//        JSONObject jsonObject = restClientUtil.sendGetMethod(osrmUtil.createOSRMRequestUrl(
//                schoolAddress.getLatitude(), schoolAddress.getLongitude(),
//                jobPostingAddress.getLatitude(), jobPostingAddress.getLongitude()),
//                null
//        );
//        log.info("OSRM Response: {}", jsonObject.toString());
//
//        RouteResponseDto routeResponseDto = osrmUtil.mapToRouteResponseDto(jsonObject);
//        log.info("RouteResponseDto: {}", routeResponseDto.toString());
//
//        if(graduationSchool.getIsMetropolitan()){
//            return routeResponseDto.routes().get(0).duration() < Double.parseDouble(METROPOLITAN_DURATION);
//        }else{
//            return routeResponseDto.routes().get(0).duration() < Double.parseDouble(NON_METROPOLITAN_DURATION);
//        }
//    }
}
