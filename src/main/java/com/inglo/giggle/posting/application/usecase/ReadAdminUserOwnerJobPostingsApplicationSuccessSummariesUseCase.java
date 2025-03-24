package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.application.dto.response.ReadAdminUserOwnerJobPostingsApplicationSuccessSummariesResponseDto;

@UseCase
public interface ReadAdminUserOwnerJobPostingsApplicationSuccessSummariesUseCase {

    /**
     * 6.17 (어드민) 매칭 성공 수 조회하기
     */
    ReadAdminUserOwnerJobPostingsApplicationSuccessSummariesResponseDto execute(
            String stringStartDate,
            String stringEndDate
    );
}
