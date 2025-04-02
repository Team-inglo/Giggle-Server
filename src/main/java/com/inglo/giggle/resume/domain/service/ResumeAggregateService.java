package com.inglo.giggle.resume.domain.service;

import com.inglo.giggle.resume.domain.ResumeAggregate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ResumeAggregateService {

    public Map<String, Integer> calculateWorkHours(ResumeAggregate resumeAggregate) {
        return resumeAggregate.calculateWorkHours();
    }
}
