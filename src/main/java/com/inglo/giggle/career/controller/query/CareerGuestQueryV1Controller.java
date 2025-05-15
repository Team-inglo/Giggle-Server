package com.inglo.giggle.career.controller.query;

import com.inglo.giggle.career.application.dto.response.ReadGuestsCareersDetailsResponseDto;
import com.inglo.giggle.career.application.dto.response.ReadGuestsCareersOverviewsResponseDto;
import com.inglo.giggle.career.application.usecase.ReadGuestCareersOverviewsUseCase;
import com.inglo.giggle.career.application.usecase.ReadGuestsCareersDetailsUseCase;
import com.inglo.giggle.core.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class CareerGuestQueryV1Controller {

    private final ReadGuestCareersOverviewsUseCase readGuestCareersOverviewsUseCase;
    private final ReadGuestsCareersDetailsUseCase readGuestsCareersDetailsUseCase;

    /**
     * 14.1 (게스트) 커리어 리스트 조회
     */
    @GetMapping("/guests/careers/overviews")
    public ResponseDto<ReadGuestsCareersOverviewsResponseDto> readUsersCareersOverviews(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "sorting", required = false) String sorting,
            @RequestParam(value = "category", required = false) String category
    ) {
        return ResponseDto.ok(
                readGuestCareersOverviewsUseCase.execute(
                        page,
                        size,
                        search,
                        sorting,
                        category
                )
        );
    }

    /**
     * 14.2 (게스트) 커리어 디테일 조회
     */
    @GetMapping("/guests/careers/{career-id}/details")
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
