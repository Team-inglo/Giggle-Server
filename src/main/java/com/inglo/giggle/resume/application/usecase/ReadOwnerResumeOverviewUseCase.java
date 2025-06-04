package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.core.type.EMajor;
import com.inglo.giggle.core.type.ENationality;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.posting.domain.type.EJobCategory;
import com.inglo.giggle.resume.application.dto.response.ReadOwnerResumeOverviewResponseDto;
import com.inglo.giggle.resume.domain.type.EKorean;

import java.util.List;
import java.util.UUID;

@UseCase
public interface ReadOwnerResumeOverviewUseCase {
    /**
     * 7.24 (고용주) 이력서 리스트 조회하기 유스케이스
     */
    ReadOwnerResumeOverviewResponseDto execute(
            UUID accountId,
            Integer page,
            Integer size,
            String sorting,
            List<EVisa> visa,
            List<EKorean> korean,
            List<EMajor> major,
            List<ENationality> nationality,
            List<EJobCategory> industry,
            Boolean isBookmarked
    );

}
