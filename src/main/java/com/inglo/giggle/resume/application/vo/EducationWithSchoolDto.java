package com.inglo.giggle.resume.application.vo;

import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.school.domain.School;

public record EducationWithSchoolDto(
        Education education,
        School school
) {
}
