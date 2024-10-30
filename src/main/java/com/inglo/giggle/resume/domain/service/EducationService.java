package com.inglo.giggle.resume.domain.service;

import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.school.domain.School;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EducationService {

    public Education createEducation(
            EEducationLevel educationLevel,
            School school,
            String major,
            Double gpa,
            LocalDate startDate,
            LocalDate endDate,
            Integer grade,
            Resume resume
    ) {
        return Education.builder()
                .educationLevel(educationLevel)
                .school(school)
                .major(major)
                .gpa(gpa)
                .enrollmentDate(startDate)
                .graduationDate(endDate)
                .grade(grade)
                .resume(resume)
                .build();
    }

    public Education updateEducation(
            Education education,
            EEducationLevel educationLevel,
            School school,
            String major,
            Double gpa,
            LocalDate startDate,
            LocalDate endDate,
            Integer grade
    ) {
        education.updateEducationLevel(educationLevel);
        education.updateSchool(school);
        education.updateMajor(major);
        education.updateGpa(gpa);
        education.updateEnrollmentDate(startDate);
        education.updateGraduationDate(endDate);
        education.updateGrade(grade);
        return education;
    }

    public EEducationLevel getEducationLevelByVisa(EVisa visa) {
        switch (visa) {
            case D_2_1 -> {
                return EEducationLevel.ASSOCIATE;
            }
            case D_2_2 -> {
                return EEducationLevel.BACHELOR;
            }
            case D_2_3 -> {
                return EEducationLevel.MASTER;
            }
            case D_2_4 -> {
                return EEducationLevel.DOCTOR;
            }
            default -> {
                return EEducationLevel.HIGHSCHOOL;
            }
        }
    }
}
