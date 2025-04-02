package com.inglo.giggle.version.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.version.presentation.dto.response.ReadRecentVersionResponseDto;
import com.inglo.giggle.version.domain.type.EOsType;

@UseCase
public interface ReadRecentVersionUseCase {

    /**
     * 0.1 최근 버전 조회
     */
    ReadRecentVersionResponseDto execute(
            EOsType osType
    );
}
