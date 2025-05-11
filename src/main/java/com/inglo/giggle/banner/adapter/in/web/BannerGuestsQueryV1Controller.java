package com.inglo.giggle.banner.adapter.in.web;

import com.inglo.giggle.banner.application.port.in.query.ReadGuestBannerDetailQuery;
import com.inglo.giggle.banner.application.port.in.query.ReadGuestBannerOverviewQuery;
import com.inglo.giggle.banner.application.port.in.result.ReadGuestBannerDetailResult;
import com.inglo.giggle.banner.application.port.in.result.ReadGuestBannerOverviewResult;
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
    private final ReadGuestBannerOverviewQuery readGuestBannerOverviewQuery;
    private final ReadGuestBannerDetailQuery readGuestBannerDetailQuery;

    /**
     * 12.1 (게스트) 배너 요약 조회하기
     */
    @GetMapping("/overviews")
    public ResponseDto<ReadGuestBannerOverviewResult> readBannerOverview() {

        return ResponseDto.ok(readGuestBannerOverviewQuery.execute());
    }

    /**
     * 12.2 (게스트) 배너 상세 조회하기
     */
    @GetMapping("/{id}/details")
    public ResponseDto<ReadGuestBannerDetailResult> readBannerDetail(
            @PathVariable Long id
    ) {
        return ResponseDto.ok(readGuestBannerDetailQuery.execute(id));
    }

}
