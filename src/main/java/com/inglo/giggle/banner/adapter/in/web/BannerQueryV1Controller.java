package com.inglo.giggle.banner.adapter.in.web;

import com.inglo.giggle.banner.application.port.in.query.ReadBannerDetailQuery;
import com.inglo.giggle.banner.application.port.in.query.ReadBannerOverviewQuery;
import com.inglo.giggle.banner.application.port.in.result.ReadBannerDetailResult;
import com.inglo.giggle.banner.application.port.in.result.ReadBannerOverviewResult;
import com.inglo.giggle.core.annotation.security.Role;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/banners")
public class BannerQueryV1Controller {
    private final ReadBannerOverviewQuery readBannerOverviewQuery;
    private final ReadBannerDetailQuery readBannerDetailQuery;

    /**
     * 12.3 (유학생/고용주) 배너 요약 조회하기
     */
    @GetMapping("/overviews")
    public ResponseDto<ReadBannerOverviewResult> readBannerOverview(
            @Role ESecurityRole role
    ) {
        return ResponseDto.ok(readBannerOverviewQuery.execute(role));
    }

    /**
     * 12.4 (유학생/고용주) 배너 상세 조회하기
     */
    @GetMapping("/{id}/details")
    public ResponseDto<ReadBannerDetailResult> readBannerDetail(
            @Role ESecurityRole role,
            @PathVariable Long id
    ) {
        return ResponseDto.ok(readBannerDetailQuery.execute(role, id));
    }

}
