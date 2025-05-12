package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.utility.DateTimeUtil;
import com.inglo.giggle.resume.application.port.in.query.ReadUserEducationDetailQuery;
import com.inglo.giggle.resume.application.port.in.result.ReadUserEducationDetailResult;
import com.inglo.giggle.resume.application.port.out.LoadResumePort;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.school.application.port.in.query.ReadUserSchoolBriefByIdsQuery;
import com.inglo.giggle.school.application.port.in.result.ReadUserSchoolBriefByIdsResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserEducationDetailService implements ReadUserEducationDetailQuery {

    private final LoadResumePort loadResumePort;
    private final ReadUserSchoolBriefByIdsQuery readUserSchoolBriefByIdsQuery;

    @Override
    @Transactional(readOnly = true)
    public ReadUserEducationDetailResult execute(UUID accountId, Long educationId) {

        // Resume 조회
        Resume resume = loadResumePort.loadResumeWithEducationsOrElseThrow(accountId);

        // Education 유효성 체크
        resume.checkEducationValidation(educationId);

        // Education 조회
        Education education = resume.getEducation(educationId);

        // School 조회
        ArrayList<Long> schoolIds = new ArrayList<>();
        schoolIds.add(education.getSchoolId());
        ReadUserSchoolBriefByIdsResult schoolBriefResult = readUserSchoolBriefByIdsQuery.execute(
                schoolIds
        );

        Long schoolId = schoolBriefResult.getSchoolList().get(0).getId();
        String schoolName = schoolBriefResult.getSchoolList().get(0).getName();

        return ReadUserEducationDetailResult.of(
                education.getEducationLevel(),
                education.getMajor(),
                education.getGpa(),
                DateTimeUtil.convertLocalDateToString(education.getEnrollmentDate()),
                education.getGraduationDate() != null ? DateTimeUtil.convertLocalDateToString(education.getGraduationDate()) : null,
                education.getGrade(),
                schoolId,
                schoolName
        );
    }
}
