package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.posting.application.dto.response.ReadAdminUserOwnerJobPostingsOverviewsResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadAdminUserOwnerJobPostingsOverviewsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadAdminUserOwnerJobPostingsOverviewsService implements ReadAdminUserOwnerJobPostingsOverviewsUseCase {

    @Override
    public ReadAdminUserOwnerJobPostingsOverviewsResponseDto execute(Integer page, Integer size, String search, String startDate, String endDate, String filterType, String filter, String sortType, String sort) {
        return null;
    }
}
