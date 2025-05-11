package com.inglo.giggle.user.application.port.in.command;

import com.inglo.giggle.core.dto.AddressRequestDto;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class UpdateUserSelfCommand extends SelfValidating<UpdateUserSelfCommand> {

    @NotNull(message = "AccountID는 필수입니다.")
    private final UUID accountId;

    private final MultipartFile image;

    @NotNull(message = "이름은 필수입니다.")
    @Size(min = 1, max = 50)
    private final String firstName;

    @NotNull(message = "성은 필수입니다.")
    @Size(min = 1, max = 100)
    private final String lastName;

    private final LocalDate birth;

    @NotNull(message = "성별은 필수입니다.")
    private final String gender;

    @Size(min = 1, max = 56, message = "국적은 1자 이상 56자 이하로 입력해주세요.")
    private final String nationality;

    @NotNull(message = "비자 종류는 필수입니다.")
    private final String visa;

    @NotNull(message = "전화번호는 필수입니다.")
    @Size(min = 10, max = 20)
    private final String phoneNumber;

    @NotNull(message = "프로필 이미지 변경 여부는 필수입니다.")
    private final Boolean isProfileImgChanged;

    @Valid
    private final AddressRequestDto address;

    public UpdateUserSelfCommand(
            UUID accountId,
            MultipartFile image,
            String firstName,
            String lastName,
            LocalDate brith,
            String gender,
            String nationality,
            String visa,
            String phoneNumber,
            Boolean isProfileImgChanged,
            AddressRequestDto address
    ) {
        this.accountId = accountId;
        this.image = image;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birth = brith;
        this.gender = gender;
        this.nationality = nationality;
        this.visa = visa;
        this.phoneNumber = phoneNumber;
        this.isProfileImgChanged = isProfileImgChanged;
        this.address = address;

        this.validateSelf();
    }

}
