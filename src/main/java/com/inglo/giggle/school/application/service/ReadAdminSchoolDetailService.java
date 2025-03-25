package com.inglo.giggle.school.application.service;

import com.inglo.giggle.school.application.dto.response.ReadAdminSchoolDetailResponseDto;
import com.inglo.giggle.school.application.usecase.ReadAdminSchoolDetailUseCase;
import com.inglo.giggle.school.domain.School;
import com.inglo.giggle.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReadAdminSchoolDetailService implements ReadAdminSchoolDetailUseCase {

    private final SchoolRepository schoolRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadAdminSchoolDetailResponseDto execute(Long id) {

        School school = schoolRepository.findByIdOrElseThrow(id);

        return ReadAdminSchoolDetailResponseDto.of(school);
    }
}
