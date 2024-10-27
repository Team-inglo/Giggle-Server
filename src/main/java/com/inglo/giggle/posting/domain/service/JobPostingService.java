package com.inglo.giggle.posting.domain.service;

import com.inglo.giggle.posting.domain.JobPosting;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JobPostingService {

    public Map<String, Integer> calculateWorkHours(JobPosting jobPosting) {
        return jobPosting.calculateWorkHours();
    }
}
