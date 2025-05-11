package com.inglo.giggle.term.adapter.in.web;

import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.term.adapter.in.web.dto.CreateAdminTermRequestDto;
import com.inglo.giggle.term.application.port.in.command.CreateAdminTermCommand;
import com.inglo.giggle.term.application.port.in.usecase.CreateAdminTermUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admins/terms")
public class TermAdminCommandV1Controller {

    private final CreateAdminTermUseCase createAdminTermUseCase;

    /**
     * 11.3 (어드민) 약관 생성하기
     */
    @PostMapping("")
    public ResponseDto<Void> createTermAccount(
            @RequestBody @Valid CreateAdminTermRequestDto requestDto
    ) {
        CreateAdminTermCommand command = new CreateAdminTermCommand(
                requestDto.version(),
                requestDto.content(),
                requestDto.termType()
        );
        createAdminTermUseCase.execute(command);
        return ResponseDto.created(null);
    }
}
