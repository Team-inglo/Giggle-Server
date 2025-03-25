package com.inglo.giggle.term.controller.command;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.term.application.dto.request.CreateTermAccountRequestDto;
import com.inglo.giggle.term.application.usecase.CreateTermAccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class TermCommandV1Controller {

    private final CreateTermAccountUseCase createTermAccountUseCase;

    /**
     * 11.2 약관 동의하기
     */
    @PostMapping("/term-accounts")
    public ResponseDto<Void> createTermAccount(
            @AccountID UUID accountId,
            @RequestBody CreateTermAccountRequestDto requestDto
    ) {
        createTermAccountUseCase.execute(accountId, requestDto);
        return ResponseDto.created(null);
    }
}
