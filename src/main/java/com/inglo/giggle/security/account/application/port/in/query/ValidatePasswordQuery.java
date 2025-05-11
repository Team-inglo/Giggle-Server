package com.inglo.giggle.security.account.application.port.in.query;

import com.inglo.giggle.security.account.adapter.in.web.dto.ValidatePasswordRequestDto;
import com.inglo.giggle.security.account.application.port.in.result.ValidatePasswordResponseDto;

import java.util.UUID;

public interface ValidatePasswordQuery {
    ValidatePasswordResponseDto execute(UUID accountId, ValidatePasswordRequestDto requestDto);
}
