package com.inglo.giggle.posting.domain.service;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.posting.domain.JobPostAggregate;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.resume.domain.Resume;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JobPostAggregateService {

    public JobPostAggregate createJobPostAggregate(Resume resume, JobPosting jobPosting) {
        return JobPostAggregate.builder()
                .resume(resume)
                .jobPosting(jobPosting)
                .build();
    }

    public Boolean isUserApplicable(
            JobPostAggregate jobPostAggregate,
            Map<String, Integer> userWorkHours,
            Map<String, Integer> jobWorkHours
    ) {
        Boolean isUserApplicableByWorkTime = jobPostAggregate.isUserApplicableByWorkTime(userWorkHours, jobWorkHours);
        Boolean isUserApplicableByJobCategory = jobPostAggregate.isUserApplicableByJobCategory();

        return isUserApplicableByJobCategory && isUserApplicableByWorkTime;
    }
}
