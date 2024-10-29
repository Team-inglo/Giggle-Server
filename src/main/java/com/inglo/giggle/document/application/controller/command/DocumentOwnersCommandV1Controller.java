package com.inglo.giggle.document.application.controller.command;

import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.document.application.dto.request.UpdateOwnerPartTimeEmploymentPermitRequestDto;
import com.inglo.giggle.document.application.usecase.UpdateOwnerPartTimeEmploymentPermitUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/owners")
public class DocumentOwnersCommandV1Controller {

    private final UpdateOwnerPartTimeEmploymentPermitUseCase updateOwnerPartTimeEmploymentPermitUseCase;

    /**
     * 8.11 (고용주) 시간제 취업허가서 수정하기
     */
    @PutMapping("/documents/{id}/part-time-employment-permits")
    public ResponseDto<Void> updateOwnerPartTimeEmploymentPermit(
            @PathVariable Long id,
            @RequestBody @Valid UpdateOwnerPartTimeEmploymentPermitRequestDto requestDto
    ) {
        updateOwnerPartTimeEmploymentPermitUseCase.execute(id, requestDto);
        return ResponseDto.ok(null);
    }
}
