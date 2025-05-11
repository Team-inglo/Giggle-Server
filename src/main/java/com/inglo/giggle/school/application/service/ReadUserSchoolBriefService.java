package com.inglo.giggle.school.application.service;

import com.inglo.giggle.school.application.port.in.query.ReadUserSchoolBriefQuery;
import com.inglo.giggle.school.application.port.in.result.ReadUserSchoolBriefResult;
import com.inglo.giggle.school.application.port.out.LoadSchoolPort;
import com.inglo.giggle.school.domain.School;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReadUserSchoolBriefService implements ReadUserSchoolBriefQuery {

    private final LoadSchoolPort loadSchoolPort;

    @Override
    public ReadUserSchoolBriefResult execute(String search, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<School> schoolsPage = loadSchoolPort.loadSchool(pageable, search);

        List<ReadUserSchoolBriefResult.SchoolListDto> schoolList = schoolsPage.getContent().stream()
                .map(school -> ReadUserSchoolBriefResult.SchoolListDto.builder()
                        .id(school.getId())
                        .name(school.getSchoolName())
                        .phoneNumber(school.getSchoolPhoneNumber())
                        .build())
                .collect(Collectors.toList());

        return ReadUserSchoolBriefResult.builder()
                .schoolList(schoolList)
                .hasNext(schoolsPage.hasNext())
                .build();
    }
}
