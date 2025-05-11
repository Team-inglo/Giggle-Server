package com.inglo.giggle.owner.adapter.in.web;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.owner.application.port.in.query.ReadOwnerBriefQuery;
import com.inglo.giggle.owner.application.port.in.query.ReadOwnerDetailQuery;
import com.inglo.giggle.owner.application.port.in.result.ReadOwnerBriefResult;
import com.inglo.giggle.owner.application.port.in.result.ReadOwnerDetailResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/owners")
public class OwnerQueryV1Controller {

    private final ReadOwnerDetailQuery readOwnerDetailQuery;
    private final ReadOwnerBriefQuery readOwnerBriefQuery;

    /**
     * 3.2 (고용주) 회사 정보 조회하기
     */
    @GetMapping("/details")
    public ResponseDto<ReadOwnerDetailResult> readOwnerDetail(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readOwnerDetailQuery.execute(accountId));
    }

    /**
     * 3.4 (고용주) 고용주 간단 정보 조회하기
     */
    @GetMapping("/briefs")
    public ResponseDto<ReadOwnerBriefResult> readOwnerBrief(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readOwnerBriefQuery.execute(accountId));
    }
}
