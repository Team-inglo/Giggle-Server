package com.inglo.giggle.resume.domain.service;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.ResumeAggregate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ResumeAggregateService {

    public ResumeAggregate createResumeAggregate(User user, Resume resume, Education education) {
        return ResumeAggregate.builder()
                .user(user)
                .resume(resume)
                .education(education)
                .build();
    }

    public Map<String, Integer> calculateWorkHours(ResumeAggregate resumeAggregate) {
        return resumeAggregate.calculateWorkHours();
    }
}
