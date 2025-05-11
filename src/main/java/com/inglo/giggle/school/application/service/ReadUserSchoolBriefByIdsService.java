package com.inglo.giggle.school.application.service;

import com.inglo.giggle.school.application.port.in.query.ReadUserSchoolBriefByIdsQuery;
import com.inglo.giggle.school.application.port.in.result.ReadUserSchoolBriefByIdsResult;
import com.inglo.giggle.school.application.port.out.LoadSchoolPort;
import com.inglo.giggle.school.domain.School;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadUserSchoolBriefByIdsService implements ReadUserSchoolBriefByIdsQuery {

    private final LoadSchoolPort loadSchoolPort;

    @Override
    public ReadUserSchoolBriefByIdsResult execute(List<Long> ids) {
        List<School> schoolList = loadSchoolPort.loadSchools(ids);

        return ReadUserSchoolBriefByIdsResult.of(
                schoolList.stream()
                        .map(school -> ReadUserSchoolBriefByIdsResult.SchoolListDto.of(school.getId(), school.getSchoolName()))
                        .toList()
        );
    }
}
