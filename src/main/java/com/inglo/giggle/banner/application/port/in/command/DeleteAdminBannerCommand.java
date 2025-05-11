package com.inglo.giggle.banner.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DeleteAdminBannerCommand extends SelfValidating<DeleteAdminBannerCommand> {

    @NotNull(message = "배너 ID는 필수입니다.")
    private final Long bannerId;

    public DeleteAdminBannerCommand(Long bannerId) {
        this.bannerId = bannerId;
        this.validateSelf();
    }
}
