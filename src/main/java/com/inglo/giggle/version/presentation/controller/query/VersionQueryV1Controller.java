package com.inglo.giggle.version.presentation.controller.query;

import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.version.presentation.dto.response.ReadRecentVersionResponseDto;
import com.inglo.giggle.version.application.usecase.ReadRecentVersionUseCase;
import com.inglo.giggle.version.domain.type.EOsType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/versions")
public class VersionQueryV1Controller {

    private final ReadRecentVersionUseCase readRecentVersionUseCase;

    /**
     * 0.1 최신 버전 조회하기
     */
    @GetMapping
    public ResponseDto<ReadRecentVersionResponseDto> getRecentVersion(
            @RequestParam(value = "os", defaultValue = "IOS") String osType
    ) {
        return ResponseDto.ok(
                readRecentVersionUseCase.execute(
                        EOsType.valueOf(osType)
                )
        );
    }
}
