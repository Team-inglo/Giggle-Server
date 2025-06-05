package com.inglo.giggle.career.application.usecase;

import com.inglo.giggle.career.application.dto.response.ReadAdminsCareersDetailsResponseDto;
import com.inglo.giggle.career.application.dto.response.ReadGuestsCareersDetailsResponseDto;
import com.inglo.giggle.core.annotation.bean.UseCase;

@UseCase
public interface ReadAdminsCareersDetailsUseCase {

    ReadAdminsCareersDetailsResponseDto execute(
            Long careerId
    );
}
