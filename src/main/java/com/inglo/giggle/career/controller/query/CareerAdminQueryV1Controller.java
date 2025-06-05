package com.inglo.giggle.career.controller.query;

import com.inglo.giggle.career.application.dto.response.ReadAdminsCareersOverviewsResponseDto;
import com.inglo.giggle.career.application.dto.response.ReadGuestsCareersDetailsResponseDto;
import com.inglo.giggle.career.application.usecase.ReadAdminsCareersOverviewsUseCase;
import com.inglo.giggle.career.application.usecase.ReadGuestsCareersDetailsUseCase;
import com.inglo.giggle.core.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class CareerAdminQueryV1Controller {

    private final ReadAdminsCareersOverviewsUseCase readAdminsCareersOverviewsUseCase;
    private final ReadGuestsCareersDetailsUseCase readGuestsCareersDetailsUseCase;

    /**
     * 14.7 (어드민) 커리어 리스트 조회
     */
    @GetMapping("/admins/careers/overviews")
    public ResponseDto<ReadAdminsCareersOverviewsResponseDto> readAdminsCareersOverviews(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "sorting", required = false) String sorting,
            @RequestParam(value = "category", required = false) String category
    ) {
        return ResponseDto.ok(
                readAdminsCareersOverviewsUseCase.execute(
                        page,
                        size,
                        search,
                        sorting,
                        category
                )
        );
    }

    /**
     * 14.2 (어드민) 커리어 디테일 조회
     */
    @GetMapping("/admins/careers/{career-id}/details")
    public ResponseDto<ReadGuestsCareersDetailsResponseDto> readGuestsCareersDetails(
            @PathVariable(value = "career-id") Long careerId
    ) {
        return ResponseDto.ok(
                readGuestsCareersDetailsUseCase.execute(
                        careerId
                )
        );
    }
}
