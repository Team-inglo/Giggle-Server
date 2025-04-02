package com.inglo.giggle.banner.presentation.controller.query;

import com.inglo.giggle.banner.presentation.dto.response.ReadBannerDetailResponseDto;
import com.inglo.giggle.banner.presentation.dto.response.ReadBannerOverviewResponseDto;
import com.inglo.giggle.banner.application.usecase.ReadGuestBannerDetailUseCase;
import com.inglo.giggle.banner.application.usecase.ReadGuestBannerOverviewUseCase;
import com.inglo.giggle.core.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/guests/banners")
public class BannerGuestsQueryV1Controller {
    private final ReadGuestBannerOverviewUseCase readGuestBannerOverviewUseCase;
    private final ReadGuestBannerDetailUseCase readGuestBannerDetailUseCase;

    /**
     * 12.1 (게스트) 배너 요약 조회하기
     */
    @GetMapping("/overviews")
    public ResponseDto<ReadBannerOverviewResponseDto> readBannerOverview() {

        return ResponseDto.ok(readGuestBannerOverviewUseCase.execute());
    }

    /**
     * 12.2 (게스트) 배너 상세 조회하기
     */
    @GetMapping("/{id}/details")
    public ResponseDto<ReadBannerDetailResponseDto> readBannerDetail(
            @PathVariable Long id
    ) {
        return ResponseDto.ok(readGuestBannerDetailUseCase.execute(id));
    }

}
