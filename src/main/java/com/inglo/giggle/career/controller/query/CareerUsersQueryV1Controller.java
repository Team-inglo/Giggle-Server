package com.inglo.giggle.career.controller.query;

import com.inglo.giggle.career.application.dto.response.ReadUsersCareersDetailsResponseDto;
import com.inglo.giggle.career.application.dto.response.ReadUsersCareersOverviewsResponseDto;
import com.inglo.giggle.career.application.usecase.ReadUsersCareersDetailsUseCase;
import com.inglo.giggle.career.application.usecase.ReadUsersCareersOverviewsUseCase;
import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class CareerUsersQueryV1Controller {

    private final ReadUsersCareersOverviewsUseCase readUsersCareersOverviewsUseCase;
    private final ReadUsersCareersDetailsUseCase readUsersCareersDetailsUseCase;

    /*
     * 14.3 (유학생) 커리어 리스트 조회
     */
    @GetMapping("/users/careers/overviews")
    public ResponseDto<ReadUsersCareersOverviewsResponseDto> readUsersCareersOverviews(
            @AccountID UUID accountId,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "sorting", required = false) String sorting,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "is-book-marked") boolean isBookmarked

    ) {
        if (isBookmarked) {
            return ResponseDto.ok(
                    readUsersCareersOverviewsUseCase.execute(
                            accountId,
                            page,
                            size
                    )
            );
        }

        return ResponseDto.ok(
                readUsersCareersOverviewsUseCase.execute(
                        accountId,
                        page,
                        size,
                        search,
                        sorting,
                        category
                )
        );
    }

    /**
     * 14.4 (유학생) 커리어 디테일 조회
     */
    @GetMapping("/users/careers/{career-id}/details")
    public ResponseDto<ReadUsersCareersDetailsResponseDto> readUsersCareersDetails(
            @AccountID UUID accountId,
            @PathVariable(value = "career-id") Long careerId
    ) {
        return ResponseDto.ok(
                readUsersCareersDetailsUseCase.execute(
                        accountId,
                        careerId
                )
        );
    }
}
