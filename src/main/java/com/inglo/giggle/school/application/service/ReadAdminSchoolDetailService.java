package com.inglo.giggle.school.application.service;

import com.inglo.giggle.core.dto.AddressResponseDto;
import com.inglo.giggle.school.application.port.in.query.ReadAdminSchoolDetailQuery;
import com.inglo.giggle.school.application.port.in.result.ReadAdminSchoolDetailResult;
import com.inglo.giggle.school.application.port.out.LoadSchoolPort;
import com.inglo.giggle.school.domain.School;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReadAdminSchoolDetailService implements ReadAdminSchoolDetailQuery {

    private final LoadSchoolPort loadSchoolPort;

    @Override
    @Transactional(readOnly = true)
    public ReadAdminSchoolDetailResult execute(Long id) {

        School school = loadSchoolPort.loadSchool(id);

        return ReadAdminSchoolDetailResult.of(
                school.getId(),
                school.getSchoolName(),
                school.getSchoolPhoneNumber(),
                school.getIsMetropolitan(),
                school.getInstituteName(),
                school.getCoordinatorName(),
                school.getCoordinatorPhoneNumber(),
                AddressResponseDto.from(school.getAddress())
        );
    }
}
