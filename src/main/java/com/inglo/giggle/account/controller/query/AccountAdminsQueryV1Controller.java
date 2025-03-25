package com.inglo.giggle.account.controller.query;

import com.inglo.giggle.account.application.dto.response.ReadAdminAccountSignUpOverviewResponseDto;
import com.inglo.giggle.account.application.dto.response.ReadAdminAccountWithdrawalOverviewResponseDto;
import com.inglo.giggle.account.application.usecase.ReadAdminAccountOverviewUseCase;
import com.inglo.giggle.account.application.usecase.ReadAdminAccountSignUpOverviewUseCase;
import com.inglo.giggle.account.application.usecase.ReadAdminAccountWithdrawalOverviewUseCase;
import com.inglo.giggle.core.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admins/accounts")
public class AccountAdminsQueryV1Controller {

    private final ReadAdminAccountSignUpOverviewUseCase readAdminAccountSignUpOverviewUseCase;
    private final ReadAdminAccountWithdrawalOverviewUseCase readAdminAccountWithdrawalOverviewUseCase;
    private final ReadAdminAccountOverviewUseCase readAdminAccountOverviewUseCase;

    /**
     * 3.9 (어드민) 가입 회원 통계 조회하기
     */
    @GetMapping("/sign-up/summaries")
    public ResponseDto<ReadAdminAccountSignUpOverviewResponseDto> readAdminAccountSignUpOverview(
            @RequestParam("start_date") LocalDate startDate,
            @RequestParam("end_date") LocalDate endDate
    ) {
        return ResponseDto.ok(readAdminAccountSignUpOverviewUseCase.execute(startDate, endDate));
    }

    /**
     * 3.10 (어드민) 탈퇴 회원 통계 조회하기
     */
    @GetMapping("/withdrawal/summaries")
    public ResponseDto<ReadAdminAccountWithdrawalOverviewResponseDto> readAdminAccountWithdrawalOverview(
            @RequestParam("start_date") LocalDate startDate,
            @RequestParam("end_date") LocalDate endDate
    ) {
        return ResponseDto.ok(readAdminAccountWithdrawalOverviewUseCase.execute(startDate, endDate));
    }

    /**
     * 3.11 (어드민) 회원 목록 조회하기
     */
    @GetMapping("/overviews")
    public ResponseDto<?> readAdminAccountOverview(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "start_date", required = false) LocalDate startDate,
            @RequestParam(value = "end_date", required = false) LocalDate endDate,
            @RequestParam(value = "filter_type", required = false) String filterType,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sort_type", required = false) String sortType,
            @RequestParam(value = "sort", required = false) Direction sort
            ) {
        return ResponseDto.ok(readAdminAccountOverviewUseCase.execute(
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

}
