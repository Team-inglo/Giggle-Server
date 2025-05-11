package com.inglo.giggle.school.application.port.in.command;

import com.inglo.giggle.core.dto.AddressRequestDto;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateAdminSchoolCommand extends SelfValidating<CreateAdminSchoolCommand> {

    @NotBlank(message = "학교 이름은 필수입니다.")
    private final String schoolName;

    @NotBlank(message = "학교 전화번호는 필수입니다.")
    private final String schoolPhoneNumber;

    @NotNull(message = "수도권 여부는 필수입니다.")
    private final Boolean isMetropolitan;

    private final String instituteName;

    private final String coordinatorName;

    private final String coordinatorPhoneNumber;

    @Valid
    private final AddressRequestDto address;

    public CreateAdminSchoolCommand(
            String schoolName,
            String schoolPhoneNumber,
            Boolean isMetropolitan,
            String instituteName,
            String coordinatorName,
            String coordinatorPhoneNumber,
            AddressRequestDto address
    ) {
        this.schoolName = schoolName;
        this.schoolPhoneNumber = schoolPhoneNumber;
        this.isMetropolitan = isMetropolitan;
        this.instituteName = instituteName;
        this.coordinatorName = coordinatorName;
        this.coordinatorPhoneNumber = coordinatorPhoneNumber;
        this.address = address;

        this.validateSelf();
    }
}
