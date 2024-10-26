package com.inglo.giggle.account.application.usecase;

import com.inglo.giggle.account.application.dto.request.UpdateUserRequestDto;
import com.inglo.giggle.core.annotation.bean.UseCase;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@UseCase
public interface UpdateUserUseCase {
    void execute(UUID accountId, UpdateUserRequestDto requestDto, MultipartFile image);
}
