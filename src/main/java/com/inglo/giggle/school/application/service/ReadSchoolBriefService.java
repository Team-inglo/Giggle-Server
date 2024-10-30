package com.inglo.giggle.school.application.service;

import com.inglo.giggle.school.application.dto.response.ReadSchoolBriefResponseDto;
import com.inglo.giggle.school.application.usecase.ReadSchoolBriefUseCase;
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
public class ReadSchoolBriefService implements ReadSchoolBriefUseCase {

    private final SchoolRepository schoolRepository;

    @Override
    public ReadSchoolBriefResponseDto execute(String search, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<School> schoolsPage = schoolRepository.findBySchoolNameContaining(search, pageable);

        List<ReadSchoolBriefResponseDto.SchoolListDto> schoolList = schoolsPage.getContent().stream()
                .map(school -> ReadSchoolBriefResponseDto.SchoolListDto.builder()
                        .id(school.getId())
                        .name(school.getSchoolName())
                        .phoneNumber(school.getSchoolPhoneNumber())
                        .build())
                .collect(Collectors.toList());

        return ReadSchoolBriefResponseDto.builder()
                .schoolList(schoolList)
                .hasNext(schoolsPage.hasNext())
                .build();
    }
}
