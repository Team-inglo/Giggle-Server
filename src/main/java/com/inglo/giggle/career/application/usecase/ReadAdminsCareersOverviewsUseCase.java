package com.inglo.giggle.career.application.usecase;

import com.inglo.giggle.career.application.dto.response.ReadAdminsCareersOverviewsResponseDto;
import com.inglo.giggle.core.annotation.bean.UseCase;

@UseCase
public interface ReadAdminsCareersOverviewsUseCase {

    ReadAdminsCareersOverviewsResponseDto execute(
            int page,
            int size,
            String search,
            String sorting,
            String category
    );
}
