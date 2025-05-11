package com.inglo.giggle.school.application.service;

import com.inglo.giggle.core.dto.PageInfoDto;
import com.inglo.giggle.school.application.port.in.query.ReadAdminSchoolOverviewQuery;
import com.inglo.giggle.school.application.port.in.result.ReadAdminSchoolOverviewResult;
import com.inglo.giggle.school.application.port.out.LoadSchoolPort;
import com.inglo.giggle.school.domain.School;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReadAdminSchoolOverviewService implements ReadAdminSchoolOverviewQuery {

    private final LoadSchoolPort loadSchoolPort;

    @Override
    @Transactional(readOnly = true)
    public ReadAdminSchoolOverviewResult execute(Integer page, Integer size, String search) {

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<School> schoolPage = loadSchoolPort.loadSchool(pageable, search);

        PageInfoDto pageInfo = PageInfoDto.of(
                schoolPage.getNumber() + 1,
                schoolPage.getContent().size(),
                schoolPage.getSize(),
                schoolPage.getTotalPages(),
                (int) schoolPage.getTotalElements()
        );

        return ReadAdminSchoolOverviewResult.of(
                schoolPage.getContent()
                        .stream()
                        .map(school -> ReadAdminSchoolOverviewResult.SchoolOverviewDto.of(
                                school.getId(),
                                school.getSchoolName(),
                                school.getSchoolPhoneNumber(),
                                school.getInstituteName(),
                                school.getCoordinatorName(),
                                school.getCoordinatorPhoneNumber(),
                                school.getAddress().getAddressName()
                        )).toList(),
                pageInfo
        );
    }
}
