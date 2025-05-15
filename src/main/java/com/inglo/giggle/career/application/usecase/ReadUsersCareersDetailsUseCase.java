package com.inglo.giggle.career.application.usecase;

import com.inglo.giggle.career.application.dto.response.ReadUsersCareersDetailsResponseDto;
import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface ReadUsersCareersDetailsUseCase {

    ReadUsersCareersDetailsResponseDto execute(
            UUID accountId,
            Long careerId
    );
}
