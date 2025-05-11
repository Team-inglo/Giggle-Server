package com.inglo.giggle.school.application.service;

import com.inglo.giggle.school.application.port.in.query.ReadAdminSchoolBriefByIdsQuery;
import com.inglo.giggle.school.application.port.in.result.ReadAdminSchoolBriefByIdsResult;
import com.inglo.giggle.school.application.port.out.LoadSchoolPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadAdminSchoolBriefByIdsService implements ReadAdminSchoolBriefByIdsQuery {

    private final LoadSchoolPort loadSchoolPort;

    @Override
    public ReadAdminSchoolBriefByIdsResult execute(List<Long> ids) {
        List<ReadAdminSchoolBriefByIdsResult.SchoolListDto> schoolList = loadSchoolPort.loadSchools(ids)
                .stream()
                .map(school -> ReadAdminSchoolBriefByIdsResult.SchoolListDto.of(school.getId(), school.getSchoolName()))
                .toList();

        return ReadAdminSchoolBriefByIdsResult.of(schoolList);
    }
}
