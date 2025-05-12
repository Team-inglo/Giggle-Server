package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.core.utility.DateTimeUtil;
import com.inglo.giggle.resume.application.port.in.dto.EducationWithSchoolNameDto;
import com.inglo.giggle.resume.application.port.in.dto.ResumeDetailDto;
import com.inglo.giggle.resume.application.port.in.query.ReadAdminResumeDetailQuery;
import com.inglo.giggle.resume.application.port.in.result.ReadAdminResumeDetailResult;
import com.inglo.giggle.resume.application.port.out.LoadResumePort;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.school.application.port.in.query.ReadAdminSchoolBriefByIdsQuery;
import com.inglo.giggle.school.application.port.in.result.ReadAdminSchoolBriefByIdsResult;
import com.inglo.giggle.user.application.port.in.query.ReadAdminUserDetailQuery;
import com.inglo.giggle.user.application.port.in.result.ReadAdminUserDetailResult;
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
public class ReadAdminResumeDetailService implements ReadAdminResumeDetailQuery {

    private final LoadResumePort loadResumePort;
    private final ReadAdminUserDetailQuery readAdminUserDetailQuery;
    private final ReadAdminSchoolBriefByIdsQuery readAdminSchoolBriefByIdsQuery;

    @Override
    @Transactional(readOnly = true)
    public ReadAdminResumeDetailResult execute(UUID resumeId) {

        // Resume 조회
        Resume resume = loadResumePort.loadAllResumeOrElseThrow(resumeId);

        // School 리스트 조회
        ReadAdminSchoolBriefByIdsResult schoolBriefResult = readAdminSchoolBriefByIdsQuery.execute(
                resume.getEducations().stream()
                        .map(Education::getSchoolId)
                        .collect(Collectors.toList())
        );

        ReadAdminUserDetailResult readUserDetailResult = readAdminUserDetailQuery.execute(resumeId);

        // Education 과 School 이름 매핑
        List<EducationWithSchoolNameDto> educationWithSchoolNameDtos = resume.getEducations().stream()
                .map(education -> {
                    String schoolName = schoolBriefResult.getSchoolList().stream()
                            .filter(school -> school.getId().equals(education.getSchoolId()))
                            .findFirst()
                            .map(ReadAdminSchoolBriefByIdsResult.SchoolListDto::getName)
                            .orElse(null);
                    return new EducationWithSchoolNameDto(education.getId(), education.getEducationLevel(), education.getMajor(), education.getGpa(), education.getEnrollmentDate(), education.getGraduationDate(), education.getGrade(), schoolName);
                })
                .toList();


        List<ReadAdminResumeDetailResult.WorkExperienceDto> workExperienceDtos;

        // WorkExperienceDtos 생성
        if (!resume.getWorkExperiences().isEmpty() ) {
            workExperienceDtos = resume.getWorkExperiences().stream()
                    .map(workExperience -> ReadAdminResumeDetailResult.WorkExperienceDto.of(
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

        List<ReadAdminResumeDetailResult.LanguageDetailDto> languageDetailDtos;

        // LanguageDetailDtos 생성
        if (!resume.getLanguageSkill().getAdditionalLanguages().isEmpty()) {
            languageDetailDtos = resume.getLanguageSkill().getAdditionalLanguages().stream()
                    .map(language -> ReadAdminResumeDetailResult.LanguageDetailDto.of(
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

        return ReadAdminResumeDetailResult.of(
                resumeDetailDto,
                workExperienceDtos,
                resume.getLanguageSkill().getTopikLevel(),
                resume.getLanguageSkill().getSocialIntegrationLevel(),
                resume.getLanguageSkill().getSejongInstituteLevel(),
                languageDetailDtos
        );
    }
}
