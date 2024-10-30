package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.request.UpdateUserSejongInstituteReqeustDto;

import java.util.UUID;

@UseCase
public interface UpdateUserSejongInstituteUseCase {
    void execute(UUID accountId, UpdateUserSejongInstituteReqeustDto requestDto);
}
