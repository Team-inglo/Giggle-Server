package com.inglo.giggle.school.application.service;

import com.inglo.giggle.core.domain.Address;
import com.inglo.giggle.school.application.port.in.command.CreateAdminSchoolCommand;
import com.inglo.giggle.school.application.port.in.usecase.CreateAdminSchoolUseCase;
import com.inglo.giggle.school.application.port.out.CreateSchoolPort;
import com.inglo.giggle.school.domain.School;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateAdminSchoolService implements CreateAdminSchoolUseCase {

    private final CreateSchoolPort createSchoolPort;

    @Override
    @Transactional
    public void execute(CreateAdminSchoolCommand command) {

        // Address 생성
        Address address = Address.builder()
                .addressName(command.getAddress().addressName())
                .addressDetail(command.getAddress().addressDetail())
                .region1DepthName(command.getAddress().region1DepthName())
                .region2DepthName(command.getAddress().region2DepthName())
                .region3DepthName(command.getAddress().region3DepthName())
                .region4DepthName(command.getAddress().region4DepthName())
                .latitude(command.getAddress().latitude())
                .longitude(command.getAddress().longitude())
                .build();

        School school = School.builder()
                .schoolName(command.getSchoolName())
                .schoolPhoneNumber(command.getSchoolPhoneNumber())
                .isMetropolitan(command.getIsMetropolitan())
                .instituteName(command.getInstituteName())
                .coordinatorName(command.getCoordinatorName())
                .coordinatorPhoneNumber(command.getCoordinatorPhoneNumber())
                .address(address)
                .build();

        createSchoolPort.createSchool(school);
    }
}
