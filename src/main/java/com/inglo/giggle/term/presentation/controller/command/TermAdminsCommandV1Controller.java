package com.inglo.giggle.term.presentation.controller.command;

import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.term.presentation.dto.request.CreateAdminTermAccountRequestDto;
import com.inglo.giggle.term.application.usecase.CreateAdminTermAccountUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admins/terms")
public class TermAdminsCommandV1Controller {

    private final CreateAdminTermAccountUseCase createAdminTermAccountUseCase;

    /**
     * 약관 등록하기
     */
    @PostMapping("")
    public ResponseDto<Void> createTermAccount(
            @RequestBody @Valid CreateAdminTermAccountRequestDto requestDto
    ) {
        createAdminTermAccountUseCase.execute(requestDto);
        return ResponseDto.created(null);
    }
}
