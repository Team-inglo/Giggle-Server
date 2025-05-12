package com.inglo.giggle.school.application.service;

import com.inglo.giggle.school.application.port.in.query.ReadSchoolBySchoolNameQuery;
import com.inglo.giggle.school.application.port.in.result.ReadSchoolBySchoolNameResult;
import com.inglo.giggle.school.application.port.out.LoadSchoolPort;
import com.inglo.giggle.school.domain.School;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadSchoolBySchoolNameService implements ReadSchoolBySchoolNameQuery {

    private final LoadSchoolPort loadSchoolPort;

    @Override
    public ReadSchoolBySchoolNameResult execute(String schoolName) {
        School school = loadSchoolPort.loadSchoolOrElseThrow(schoolName);
        return new ReadSchoolBySchoolNameResult(
                school.getId(),
                school.getSchoolName(),
                school.getSchoolPhoneNumber()
        );
    }
}
