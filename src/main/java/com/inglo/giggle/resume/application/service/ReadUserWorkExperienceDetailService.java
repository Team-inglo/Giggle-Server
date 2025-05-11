package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.utility.DateTimeUtil;
import com.inglo.giggle.resume.application.port.in.query.ReadUserWorkExperienceDetailQuery;
import com.inglo.giggle.resume.application.port.in.result.ReadUserWorkExperienceDetailResult;
import com.inglo.giggle.resume.application.port.out.LoadResumePort;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.WorkExperience;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserWorkExperienceDetailService implements ReadUserWorkExperienceDetailQuery {

    private final LoadResumePort loadResumePort;

    @Override
    @Transactional(readOnly = true)
    public ReadUserWorkExperienceDetailResult execute(UUID accountId, Long workExperienceId) {

        // Resume 조회
        Resume resume = loadResumePort.loadResume(accountId);
        WorkExperience workExperience = resume.getWorkExperience(workExperienceId);
        
        return ReadUserWorkExperienceDetailResult.of(
                workExperience.getExperienceTitle(),
                workExperience.getWorkplace(),
                DateTimeUtil.convertLocalDateToString(workExperience.getStartDate()),
                workExperience.getEndDate() != null ? DateTimeUtil.convertLocalDateToString(workExperience.getEndDate()) : null,
                workExperience.getDescription()
        );
    }

}
