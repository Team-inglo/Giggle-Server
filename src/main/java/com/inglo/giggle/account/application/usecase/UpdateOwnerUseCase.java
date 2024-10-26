package com.inglo.giggle.account.application.usecase;

import com.inglo.giggle.account.application.dto.request.UpdateOwnerRequestDto;
import com.inglo.giggle.core.annotation.bean.UseCase;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@UseCase
public interface UpdateOwnerUseCase {
    void execute(UUID accountId, UpdateOwnerRequestDto requestDto, MultipartFile multipartFile);
}
