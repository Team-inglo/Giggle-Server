package com.inglo.giggle.posting.domain.service;

import com.inglo.giggle.posting.domain.JobPostAggregate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JobPostAggregateService {

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
