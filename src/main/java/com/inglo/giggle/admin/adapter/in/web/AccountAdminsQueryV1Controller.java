package com.inglo.giggle.admin.adapter.in.web;

import com.inglo.giggle.admin.application.port.in.query.ReadAdminAccountOverviewQuery;
import com.inglo.giggle.admin.application.port.in.query.ReadAdminAccountSignUpOverviewQuery;
import com.inglo.giggle.admin.application.port.in.query.ReadAdminAccountWithdrawalOverviewQuery;
import com.inglo.giggle.admin.application.port.in.result.ReadAdminAccountSignUpOverviewResult;
import com.inglo.giggle.admin.application.port.in.result.ReadAdminAccountWithdrawalOverviewResult;
import com.inglo.giggle.core.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admins/accounts")
public class AccountAdminsQueryV1Controller {

    private final ReadAdminAccountSignUpOverviewQuery readAdminAccountSignUpOverviewQuery;
    private final ReadAdminAccountWithdrawalOverviewQuery readAdminAccountWithdrawalOverviewQuery;
    private final ReadAdminAccountOverviewQuery readAdminAccountOverviewQuery;

    /**
     * 3.9 (어드민) 가입 회원 통계 조회하기
     */
    @GetMapping("/sign-up/summaries")
    public ResponseDto<ReadAdminAccountSignUpOverviewResult> readAdminAccountSignUpOverview(
            @RequestParam("start_date") LocalDate startDate,
            @RequestParam("end_date") LocalDate endDate
    ) {
        return ResponseDto.ok(readAdminAccountSignUpOverviewQuery.execute(startDate, endDate));
    }

    /**
     * 3.10 (어드민) 탈퇴 회원 통계 조회하기
     */
    @GetMapping("/withdrawal/summaries")
    public ResponseDto<ReadAdminAccountWithdrawalOverviewResult> readAdminAccountWithdrawalOverview(
            @RequestParam("start_date") LocalDate startDate,
            @RequestParam("end_date") LocalDate endDate
    ) {
        return ResponseDto.ok(readAdminAccountWithdrawalOverviewQuery.execute(startDate, endDate));
    }

//    /**
//     * 3.11 (어드민) 회원 목록 조회하기
//     */
//    @GetMapping("/overviews")
//    public ResponseDto<?> readAdminAccountOverview(
//            @RequestParam("page") Integer page,
//            @RequestParam("size") Integer size,
//            @RequestParam(value = "search", required = false) String search,
//            @RequestParam(value = "start_date", required = false) LocalDate startDate,
//            @RequestParam(value = "end_date", required = false) LocalDate endDate,
//            @RequestParam(value = "filter_type", required = false) String filterType,
//            @RequestParam(value = "filter", required = false) String filter,
//            @RequestParam(value = "sort_type", required = false) String sortType,
//            @RequestParam(value = "sort", required = false) Direction sort
//            ) {
//        return ResponseDto.ok(readAdminAccountOverviewUseCase.execute(
//                page,
//                size,
//                search,
//                startDate,
//                endDate,
//                filterType,
//                filter,
//                sortType,
//                sort
//        ));
//    }

}
