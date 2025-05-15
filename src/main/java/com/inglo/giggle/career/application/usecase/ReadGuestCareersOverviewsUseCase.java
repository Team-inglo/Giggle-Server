package com.inglo.giggle.career.application.usecase;

import com.inglo.giggle.career.application.dto.response.ReadGuestsCareersOverviewsResponseDto;
import com.inglo.giggle.core.annotation.bean.UseCase;

@UseCase
public interface ReadGuestCareersOverviewsUseCase {

    ReadGuestsCareersOverviewsResponseDto execute(
            int page,
            int size,
            String search,
            String sorting,
            String category
    );
}
