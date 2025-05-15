package com.inglo.giggle.career.controller.command;

import com.inglo.giggle.career.application.dto.response.UpdateUsersBookMarkCareerResponseDto;
import com.inglo.giggle.career.application.usecase.UpdateUsersBookMarkCareerUseCase;
import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class CareerUsersCommandV1Controller {

    private final UpdateUsersBookMarkCareerUseCase updateUsersBookMarkCareerUseCase;

    /**
     * 14.6 (유학생) 커리어 북마크 추가/삭제
     */
    @PutMapping("/users/careers/{careerId}/book-mark-careers")
    public ResponseDto<UpdateUsersBookMarkCareerResponseDto> updateUsersBookMarkCareer(
            @AccountID UUID accountId,
            @PathVariable(value = "careerId") Long careerId
    ) {
        return ResponseDto.ok(
                updateUsersBookMarkCareerUseCase.execute(
                        accountId,
                        careerId
                )
        );
    }
}
