package com.inglo.giggle.admin.application.port.in.query;

import com.inglo.giggle.admin.application.port.in.result.ReadAdminAccountOverviewResponseDto;
import com.inglo.giggle.core.annotation.bean.UseCase;
import org.springframework.data.domain.Sort.Direction;

import java.time.LocalDate;

@UseCase
public interface ReadAdminAccountOverviewUseCase {

    //TODO: 어디다가 둬야할지 모르겠음
//    /**
//     * 3.11 (어드민) 회원 목록 조회하기 UseCase
//     */
//
//    ReadAdminAccountOverviewResponseDto execute(
//            Integer page,
//            Integer size,
//            String search,
//            LocalDate startDate,
//            LocalDate endDate,
//            String filterType,
//            String filter,
//            String sortType,
//            Direction sort
//    );
}
