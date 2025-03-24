package com.inglo.giggle.school.application.service;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.school.application.dto.request.CreateAdminSchoolRequestDto;
import com.inglo.giggle.school.application.usecase.CreateAdminSchoolUseCase;
import com.inglo.giggle.school.domain.School;
import com.inglo.giggle.school.repository.mysql.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateAdminSchoolService implements CreateAdminSchoolUseCase {

    private final SchoolRepository schoolRepository;

    @Override
    @Transactional
    public void execute(CreateAdminSchoolRequestDto requestDto) {

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

        School school = School.builder()
                .schoolName(requestDto.schoolName())
                .schoolPhoneNumber(requestDto.schoolPhoneNumber())
                .isMetropolitan(requestDto.isMetropolitan())
                .instituteName(requestDto.instituteName())
                .coordinatorName(requestDto.coordinatorName())
                .coordinatorPhoneNumber(requestDto.coordinatorPhoneNumber())
                .address(address)
                .build();

        schoolRepository.save(school);
    }
}
