package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.request.UpdateUserTopikReqeustDto;

import java.util.UUID;

@UseCase
public interface UpdateUserTopikUseCase {
    void execute(UUID accountId, UpdateUserTopikReqeustDto requestDto);
}
