package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.application.dto.response.ReadAdminUserOwnerJobPostingsDigestsResponseDto;

@UseCase
public interface ReadAdminUserOwnerJobPostingsDigestsUseCase {

    /**
     * 6.19 (어드민) 신규 신청 조회하기
     */
    ReadAdminUserOwnerJobPostingsDigestsResponseDto execute(
            String startDateTime,
            String endDateTime
    );
}
