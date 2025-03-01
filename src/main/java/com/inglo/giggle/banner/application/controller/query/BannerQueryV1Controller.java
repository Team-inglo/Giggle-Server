package com.inglo.giggle.banner.application.controller.query;

import com.inglo.giggle.banner.application.dto.response.ReadBannerDetailResponseDto;
import com.inglo.giggle.banner.application.dto.response.ReadBannerOverviewResponseDto;
import com.inglo.giggle.banner.application.usecase.ReadBannerDetailUseCase;
import com.inglo.giggle.banner.application.usecase.ReadBannerOverviewUseCase;
import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.constant.Constants;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.utility.HeaderUtil;
import com.inglo.giggle.school.application.dto.response.ReadUserSchoolBriefResponseDto;
import com.inglo.giggle.school.application.dto.response.ReadUserSchoolDetailResponseDto;
import com.inglo.giggle.school.application.usecase.ReadUserSchoolBriefUseCase;
import com.inglo.giggle.school.application.usecase.ReadUserSchoolDetailUseCase;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/guests/banners")
public class BannerQueryV1Controller {
    private final ReadBannerOverviewUseCase readBannerOverviewUseCase;
    private final ReadBannerDetailUseCase readBannerDetailUseCase;

    /**
     * 12.1 (유학생/게스트/고용주) 배너 요약 조회하기
     */
    @GetMapping("/overviews")
    public ResponseDto<ReadBannerOverviewResponseDto> readBannerOverview(
            HttpServletRequest request
    ) {
        String accessToken = HeaderUtil.refineHeader(request, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX)
                .orElse(null);

        return ResponseDto.ok(readBannerOverviewUseCase.execute(accessToken));
    }

    /**
     * 12.2 (유학생/게스트/고용주) 배너 상세 조회하기
     */
    @GetMapping("/{id}/details")
    public ResponseDto<ReadBannerDetailResponseDto> readBannerDetail(
            @PathVariable Long id
    ) {
        return ResponseDto.ok(readBannerDetailUseCase.execute(id));
    }

}
