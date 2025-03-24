package com.inglo.giggle.school.application.service;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.school.application.dto.request.UpdateAdminSchoolRequestDto;
import com.inglo.giggle.school.application.usecase.UpdateAdminSchoolUseCase;
import com.inglo.giggle.school.domain.School;
import com.inglo.giggle.school.repository.mysql.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateAdminSchoolService implements UpdateAdminSchoolUseCase {

    private final SchoolRepository schoolRepository;

    @Override
    @Transactional
    public void execute(Long schoolId, UpdateAdminSchoolRequestDto requestDto) {

        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        Address address = Address.builder()
                .addressName(requestDto.address().addressName())
                .addressDetail(requestDto.address().addressDetail())
                .region1DepthName(requestDto.address().region1DepthName())
                .region2DepthName(requestDto.address().region2DepthName())
                .region3DepthName(requestDto.address().region3DepthName())
                .region4DepthName(requestDto.address().region4DepthName())
                .latitude(requestDto.address().latitude())
                .longitude(requestDto.address().longitude())
                .build();

        school.updateSelf(
                requestDto.schoolName(),
                requestDto.schoolPhoneNumber(),
                requestDto.instituteName(),
                requestDto.coordinatorName(),
                requestDto.coordinatorPhoneNumber(),
                address,
                requestDto.isMetropolitan()
        );

        schoolRepository.save(school);
    }

}
