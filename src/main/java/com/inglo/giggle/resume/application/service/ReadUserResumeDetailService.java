package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.core.utility.DateTimeUtil;
import com.inglo.giggle.resume.application.port.in.dto.EducationWithSchoolNameDto;
import com.inglo.giggle.resume.application.port.in.dto.ResumeDetailDto;
import com.inglo.giggle.resume.application.port.in.query.ReadUserResumeDetailQuery;
import com.inglo.giggle.resume.application.port.in.result.ReadUserResumeDetailResult;
import com.inglo.giggle.resume.application.port.out.LoadResumePort;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.school.application.port.in.query.ReadUserSchoolBriefByIdsQuery;
import com.inglo.giggle.school.application.port.in.result.ReadUserSchoolBriefByIdsResult;
import com.inglo.giggle.user.application.port.in.query.ReadUserSelfDetailQuery;
import com.inglo.giggle.user.application.port.in.result.ReadUserSelfDetailResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReadUserResumeDetailService implements ReadUserResumeDetailQuery {

    private final LoadResumePort loadResumePort;
    private final ReadUserSchoolBriefByIdsQuery readUserSchoolBriefByIdsQuery;
    private final ReadUserSelfDetailQuery readUserSelfDetailQuery;

    @Override
    @Transactional(readOnly = true)
    public ReadUserResumeDetailResult execute(UUID accountId) {

        // Resume 조회
        Resume resume = loadResumePort.loadResume(accountId);

        ReadUserSchoolBriefByIdsResult schoolBriefResult = readUserSchoolBriefByIdsQuery.execute(resume.getEducations().stream()
                .map(Education::getSchoolId)
                .collect(Collectors.toList()));

        ReadUserSelfDetailResult readUserDetailResult = readUserSelfDetailQuery.execute(accountId);

        // Education 과 School 이름 매핑
        List<EducationWithSchoolNameDto> educationWithSchoolNameDtos = resume.getEducations().stream()
                .map(education -> {
                    String schoolName = schoolBriefResult.getSchoolList().stream()
                            .filter(school -> school.getId().equals(education.getSchoolId()))
                            .findFirst()
                            .map(ReadUserSchoolBriefByIdsResult.SchoolListDto::getName)
                            .orElse(null);
                    return new EducationWithSchoolNameDto(education.getId(), education.getEducationLevel(), education.getMajor(), education.getGpa(), education.getEnrollmentDate(), education.getGraduationDate(), education.getGrade(), schoolName);
                })
                .toList();

        List<ReadUserResumeDetailResult.WorkExperienceDto> workExperienceDtos;

        // WorkExperienceDtos 생성
        if (!resume.getWorkExperiences().isEmpty() ) {
            workExperienceDtos = resume.getWorkExperiences().stream()
                    .map(workExperience -> ReadUserResumeDetailResult.WorkExperienceDto.of(
                            workExperience.getId(),
                            workExperience.getExperienceTitle(),
                            workExperience.getWorkplace(),
                            workExperience.getDescription(),
                            workExperience.getStartDate() != null ? DateTimeUtil.convertLocalDateToString(workExperience.getStartDate()) : null,
                            workExperience.getEndDate() != null ? DateTimeUtil.convertLocalDateToString(workExperience.getEndDate()) : null,
                            workExperience.getEndDate() != null ?
                                    (int) ChronoUnit.MONTHS.between(workExperience.getStartDate(), workExperience.getEndDate())
                                    : (int) ChronoUnit.MONTHS.between(Objects.requireNonNull(workExperience.getStartDate()), LocalDate.now())
                    )).toList();
        } else {
            workExperienceDtos = null;
        }

        List<ReadUserResumeDetailResult.LanguageDetailDto> languageDetailDtos;

        // LanguageDetailDtos 생성
        if (!resume.getLanguageSkill().getAdditionalLanguages().isEmpty()) {
            languageDetailDtos = resume.getLanguageSkill().getAdditionalLanguages().stream()
                    .map(language -> ReadUserResumeDetailResult.LanguageDetailDto.of(
                            language.getId(),
                            language.getLanguageName(),
                            language.getLevel()
                    )).toList();
        } else {
            languageDetailDtos = null;
        }

        // ResumeDetailVO 생성
        ResumeDetailDto resumeDetailDto = new ResumeDetailDto(
                resume.getAccountId(),
                resume.getIntroduction(),
                readUserDetailResult.getProfileImgUrl(),
                readUserDetailResult.getFirstName() + " " + readUserDetailResult.getLastName(),
                EVisa.fromString(readUserDetailResult.getVisa()),
                EGender.fromString(readUserDetailResult.getGender()),
                readUserDetailResult.getNationality(),
                readUserDetailResult.getBirth(),
                readUserDetailResult.getAddress().getAddressName(),
                readUserDetailResult.getAddress().getAddressDetail(),
                readUserDetailResult.getPhoneNumber(),
                readUserDetailResult.getEmail(),
                educationWithSchoolNameDtos
        );


        return ReadUserResumeDetailResult.of(
                resumeDetailDto,
                workExperienceDtos,
                resume.getLanguageSkill().getTopikLevel(),
                resume.getLanguageSkill().getSocialIntegrationLevel(),
                resume.getLanguageSkill().getSejongInstituteLevel(),
                languageDetailDtos
        );
    }
}
