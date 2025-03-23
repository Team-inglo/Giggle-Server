package com.inglo.giggle.account.application.usecase;

import com.inglo.giggle.account.application.dto.response.ReadAdminAccountOverviewResponseDto;
import com.inglo.giggle.core.annotation.bean.UseCase;
import org.springframework.data.domain.Sort.Direction;

import java.time.LocalDate;

@UseCase
public interface ReadAdminAccountOverviewUseCase {

    ReadAdminAccountOverviewResponseDto execute(
            Integer page,
            Integer size,
            String search,
            LocalDate startDate,
            LocalDate endDate,
            String filterType,
            String filter,
            String sortType,
            Direction sort
    );
}
