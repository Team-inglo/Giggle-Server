package com.inglo.giggle.school.application.service;

import com.inglo.giggle.core.domain.Address;
import com.inglo.giggle.school.application.port.in.command.UpdateAdminSchoolCommand;
import com.inglo.giggle.school.application.port.in.usecase.UpdateAdminSchoolUseCase;
import com.inglo.giggle.school.application.port.out.LoadSchoolPort;
import com.inglo.giggle.school.application.port.out.UpdateSchoolPort;
import com.inglo.giggle.school.domain.School;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateAdminSchoolService implements UpdateAdminSchoolUseCase {

    private final LoadSchoolPort loadSchoolPort;
    private final UpdateSchoolPort updateSchoolPort;

    @Override
    @Transactional
    public void execute(UpdateAdminSchoolCommand command) {

        School school = loadSchoolPort.loadSchool(command.getSchoolId());

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

        school.updateSelf(
                command.getSchoolName(),
                command.getSchoolPhoneNumber(),
                command.getInstituteName(),
                command.getCoordinatorName(),
                command.getCoordinatorPhoneNumber(),
                address,
                command.getIsMetropolitan()
        );

        updateSchoolPort.updateSchool(school);
    }

}
