package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.port.in.query.ReadUserResumeSummaryQuery;
import com.inglo.giggle.resume.application.port.in.result.ReadUserResumeSummaryResult;
import com.inglo.giggle.resume.application.port.out.LoadResumePort;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserResumeSummaryService implements ReadUserResumeSummaryQuery {

    private final LoadResumePort loadResumePort;

    @Override
    public ReadUserResumeSummaryResult execute(UUID resumeId) {
        // 이력서 요약 정보 조회
        Resume resume = loadResumePort.loadResume(resumeId);

        // 가장 최근의 학력 정보를 가져옴
        Education latestEducation = getLatestEnrollmentEducation(resume.getEducations());

        // 학력 정보가 없을 경우 null 처리
        if (latestEducation == null) {
            return ReadUserResumeSummaryResult.of(
                    resume.getLanguageSkill().getTopikLevel(),
                    resume.getLanguageSkill().getSocialIntegrationLevel(),
                    resume.getLanguageSkill().getSejongInstituteLevel()
            );
        }

        Map<String, Integer> workHours = resume.calculateWorkHours(latestEducation.getId());

        return ReadUserResumeSummaryResult.of(
                latestEducation.getGrade(),
                latestEducation.getGpa(),
                resume.getLanguageSkill().getTopikLevel(),
                resume.getLanguageSkill().getSocialIntegrationLevel(),
                resume.getLanguageSkill().getSejongInstituteLevel(),
                workHours,
                latestEducation.getSchoolId()
        );
    }

    // ------------------------------------ private methods ------------------------------------ //

    private Education getLatestEnrollmentEducation(List<Education> educations) {

        if (educations.isEmpty())
            return null;

        return educations.stream()
                .max(Comparator.comparing(Education::getEnrollmentDate))
                .orElse(null);
    }
}

