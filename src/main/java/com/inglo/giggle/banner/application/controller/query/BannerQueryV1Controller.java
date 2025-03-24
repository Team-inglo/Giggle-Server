package com.inglo.giggle.banner.application.controller.query;

import com.inglo.giggle.banner.application.dto.response.ReadBannerDetailResponseDto;
import com.inglo.giggle.banner.application.dto.response.ReadBannerOverviewResponseDto;
import com.inglo.giggle.banner.application.usecase.ReadBannerDetailUseCase;
import com.inglo.giggle.banner.application.usecase.ReadBannerOverviewUseCase;
import com.inglo.giggle.core.annotation.security.Role;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/banners")
public class BannerQueryV1Controller {
    private final ReadBannerOverviewUseCase readBannerOverviewUseCase;
    private final ReadBannerDetailUseCase readBannerDetailUseCase;

    /**
     * 12.3 (유학생/고용주) 배너 요약 조회하기
     */
    @GetMapping("/overviews")
    public ResponseDto<ReadBannerOverviewResponseDto> readBannerOverview(
            @Role ESecurityRole role
    ) {
        return ResponseDto.ok(readBannerOverviewUseCase.execute(role));
    }

    /**
     * 12.4 (유학생/고용주) 배너 상세 조회하기
     */
    @GetMapping("/{id}/details")
    public ResponseDto<ReadBannerDetailResponseDto> readBannerDetail(
            @Role ESecurityRole role,
            @PathVariable Long id
    ) {
        return ResponseDto.ok(readBannerDetailUseCase.execute(role, id));
    }

}
