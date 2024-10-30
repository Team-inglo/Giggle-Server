package com.inglo.giggle.school.application.service;

import com.inglo.giggle.school.application.dto.response.ReadUserSchoolBriefResponseDto;
import com.inglo.giggle.school.application.usecase.ReadUserSchoolBriefUseCase;
import com.inglo.giggle.school.domain.School;
import com.inglo.giggle.school.repository.mysql.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReadUserSchoolBriefService implements ReadUserSchoolBriefUseCase {

    private final SchoolRepository schoolRepository;

    @Override
    public ReadUserSchoolBriefResponseDto execute(String search, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<School> schoolsPage = schoolRepository.findBySchoolNameContaining(search, pageable);

        List<ReadUserSchoolBriefResponseDto.SchoolListDto> schoolList = schoolsPage.getContent().stream()
                .map(school -> ReadUserSchoolBriefResponseDto.SchoolListDto.builder()
                        .id(school.getId())
                        .name(school.getSchoolName())
                        .phoneNumber(school.getSchoolPhoneNumber())
                        .build())
                .collect(Collectors.toList());

        return ReadUserSchoolBriefResponseDto.builder()
                .schoolList(schoolList)
                .hasNext(schoolsPage.hasNext())
                .build();
    }
}
