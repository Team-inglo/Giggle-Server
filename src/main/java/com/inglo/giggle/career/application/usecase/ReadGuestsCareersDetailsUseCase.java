package com.inglo.giggle.career.application.usecase;

import com.inglo.giggle.career.application.dto.response.ReadGuestsCareersDetailsResponseDto;
import com.inglo.giggle.core.annotation.bean.UseCase;

@UseCase
public interface ReadGuestsCareersDetailsUseCase {

    ReadGuestsCareersDetailsResponseDto execute(
            Long careerId
    );
}
