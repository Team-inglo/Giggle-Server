package com.inglo.giggle.school.application.port.in.query;

import com.inglo.giggle.school.application.port.in.result.ReadSchoolBySchoolNameResult;

public interface ReadSchoolBySchoolNameQuery {

    ReadSchoolBySchoolNameResult execute(String schoolName);
}
