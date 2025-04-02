package com.inglo.giggle.resume.domain.service;

import com.inglo.giggle.resume.domain.Education;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class EducationService {

    public Education getLatestEducation(List<Education> educations) {

        if (educations.isEmpty())
            return null;

        return educations.stream()
                .max(Comparator.comparing(Education::getGraduationDate))
                .orElse(null);
    }
}
