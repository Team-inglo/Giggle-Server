package com.inglo.giggle.version.adapter.in.web;

import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.version.application.port.in.result.ReadRecentVersionResult;
import com.inglo.giggle.version.application.port.in.query.ReadRecentVersionQuery;
import com.inglo.giggle.version.domain.type.EOsType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/versions")
public class VersionQueryV1Controller {

    private final ReadRecentVersionQuery readRecentVersionQuery;

    /**
     * 0.1 최신 버전 조회하기
     */
    @GetMapping
    public ResponseDto<ReadRecentVersionResult> getRecentVersion(
            @RequestParam(value = "os", defaultValue = "IOS") String osType
    ) {
        return ResponseDto.ok(
                readRecentVersionQuery.execute(
                        EOsType.valueOf(osType)
                )
        );
    }
}
