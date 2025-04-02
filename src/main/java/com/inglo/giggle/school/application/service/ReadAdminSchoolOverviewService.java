package com.inglo.giggle.school.application.service;

import com.inglo.giggle.core.dto.PageInfoDto;
import com.inglo.giggle.school.domain.School;
import com.inglo.giggle.school.presentation.dto.response.ReadAdminSchoolOverviewResponseDto;
import com.inglo.giggle.school.application.usecase.ReadAdminSchoolOverviewUseCase;
import com.inglo.giggle.school.persistence.entity.SchoolEntity;
import com.inglo.giggle.school.persistence.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReadAdminSchoolOverviewService implements ReadAdminSchoolOverviewUseCase {

    private final SchoolRepository schoolRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadAdminSchoolOverviewResponseDto execute(Integer page, Integer size, String search) {

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<School> schoolPage = schoolRepository.findAllBySearch(pageable, search);

        PageInfoDto pageInfo = PageInfoDto.of(
                schoolPage.getNumber() + 1,
                schoolPage.getContent().size(),
                schoolPage.getSize(),
                schoolPage.getTotalPages(),
                (int) schoolPage.getTotalElements()
        );

        return ReadAdminSchoolOverviewResponseDto.of(
                schoolPage.getContent(),
                pageInfo
        );
    }
}
