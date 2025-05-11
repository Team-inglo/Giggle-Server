package com.inglo.giggle.banner.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
public class CreateAdminBannerCommand extends SelfValidating<CreateAdminBannerCommand> {

    @NotNull(message = "계정 ID는 필수입니다.")
    private final UUID accountId;

    private final MultipartFile image;

    @NotBlank(message = "배너 제목은 필수입니다.")
    private final String title;

    @NotBlank(message = "배너 내용은 필수입니다.")
    private final String content;

    @NotNull(message = "배너 권한은 필수입니다.")
    private final ESecurityRole role;

    public CreateAdminBannerCommand(UUID accountId, MultipartFile image, String title, String content, ESecurityRole role) {
        this.accountId = accountId;
        this.image = image;
        this.title = title;
        this.content = content;
        this.role = role;
        this.validateSelf();
    }
}
