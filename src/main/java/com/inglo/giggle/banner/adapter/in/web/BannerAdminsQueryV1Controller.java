package com.inglo.giggle.banner.adapter.in.web;

import com.inglo.giggle.banner.application.port.in.query.ReadAdminBannerDetailQuery;
import com.inglo.giggle.banner.application.port.in.query.ReadAdminBannerOverviewQuery;
import com.inglo.giggle.banner.application.port.in.result.ReadAdminBannerDetailResult;
import com.inglo.giggle.banner.application.port.in.result.ReadAdminBannerOverviewResult;
import com.inglo.giggle.core.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admins/banners")
public class BannerAdminsQueryV1Controller {

    private final ReadAdminBannerOverviewQuery readAdminBannerOverviewQuery;
    private final ReadAdminBannerDetailQuery readAdminBannerDetailQuery;

    /**
     * 12.5 (어드민) 배너 목록 조회하기
     */
    @GetMapping("/overviews")
    public ResponseDto<ReadAdminBannerOverviewResult> readAdminBannerOverview(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "start_date", required = false) LocalDate startDate,
            @RequestParam(value = "end_date", required = false) LocalDate endDate,
            @RequestParam(value = "filter_type", required = false) String filterType,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sort_type", required = false) String sortType,
            @RequestParam(value = "sort", required = false) Sort.Direction sort
    ) {
        return ResponseDto.ok(readAdminBannerOverviewQuery.execute(
                page,
                size,
                search,
                startDate,
                endDate,
                filterType,
                filter,
                sortType,
                sort
        ));
    }

    /**
     * 12.6 (어드민) 배너 상세 조회하기
     */
    @GetMapping("/{id}/details")
    public ResponseDto<ReadAdminBannerDetailResult> readAdminBannerDetail(
            @PathVariable Long id
    ) {
        return ResponseDto.ok(readAdminBannerDetailQuery.execute(id));
    }
}
