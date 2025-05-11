package com.inglo.giggle.owner.application.port.in.command;

import com.inglo.giggle.core.dto.AddressRequestDto;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
public class UpdateOwnerCommand extends SelfValidating<UpdateOwnerCommand> {

    @NotNull(message = "accountId는 필수입니다.")
    private final UUID accountId;

    private final MultipartFile image;

    @NotNull(message = "기업명은 필수입니다.")
    @Size(min = 1, max = 100)
    private final String companyName;

    @NotNull(message = "대표자명은 필수입니다.")
    @Size(min = 1, max = 10)
    private final String ownerName;

    @NotNull(message = "사업자등록번호는 필수입니다.")
    @Size(min = 1, max = 12)
    private final String companyRegistrationNumber;

    @NotNull(message = "전화번호는 필수입니다.")
    @Size(min = 10, max = 20)
    private final String phoneNumber;

    @NotNull(message = "address는 필수입니다.")
    @Valid
    AddressRequestDto address;

    @NotNull(message = "isIconImgChanged는 필수입니다.")
    Boolean isIconImgChanged;

    public UpdateOwnerCommand(UUID accountId, MultipartFile image,
            String companyName, String ownerName, String companyRegistrationNumber, String phoneNumber,
            AddressRequestDto address, Boolean isIconImgChanged) {
        this.accountId = accountId;
        this.image = image;
        this.companyName = companyName;
        this.ownerName = ownerName;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.isIconImgChanged = isIconImgChanged;

        this.validateSelf();
    }
}
