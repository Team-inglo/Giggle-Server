package com.inglo.giggle.school.application.service;

import com.inglo.giggle.school.application.port.in.query.ReadSchoolBySchoolIdQuery;
import com.inglo.giggle.school.application.port.in.result.ReadSchoolBySchoolIdResult;
import com.inglo.giggle.school.application.port.out.LoadSchoolPort;
import com.inglo.giggle.school.domain.School;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadSchoolBySchoolIdService implements ReadSchoolBySchoolIdQuery {

    private final LoadSchoolPort loadSchoolPort;

    @Override
    public ReadSchoolBySchoolIdResult execute(Long schoolId) {
        School school = loadSchoolPort.loadSchoolOrElseThrow(schoolId);
        return new ReadSchoolBySchoolIdResult(
                school.getId(),
                school.getSchoolName(),
                school.getSchoolPhoneNumber()
        );
    }
}
