package com.inglo.giggle.school.application.port.in.query;

import com.inglo.giggle.school.application.port.in.result.ReadSchoolBySchoolIdResult;

public interface ReadSchoolBySchoolIdQuery {

    ReadSchoolBySchoolIdResult execute(Long schoolId);
}
