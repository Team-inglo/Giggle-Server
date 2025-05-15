package com.inglo.giggle.career.application.usecase;

import com.inglo.giggle.career.application.dto.response.ReadUsersCareersOverviewsResponseDto;
import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface ReadUsersCareersOverviewsUseCase {

    ReadUsersCareersOverviewsResponseDto execute(
            UUID userId,
            int page,
            int size
    );

    ReadUsersCareersOverviewsResponseDto execute(
            UUID userId,
            int page,
            int size,
            String search,
            String sorting,
            String category
    );
}
