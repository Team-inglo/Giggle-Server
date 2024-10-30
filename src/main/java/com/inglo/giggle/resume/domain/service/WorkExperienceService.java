package com.inglo.giggle.resume.domain.service;

import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.WorkExperience;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class WorkExperienceService {
    public WorkExperience createWorkExperience(
            String experienceTitle,
            String workplace,
            LocalDate startDate,
            LocalDate endDate,
            String description,
            Resume resume
    ) {
        return WorkExperience.builder()
                .experienceTitle(experienceTitle)
                .workplace(workplace)
                .startDate(startDate)
                .endDate(endDate)
                .description(description)
                .resume(resume)
                .build();
    }
}
