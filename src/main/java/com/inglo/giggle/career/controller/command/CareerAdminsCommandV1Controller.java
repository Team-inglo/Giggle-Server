package com.inglo.giggle.career.controller.command;

import com.inglo.giggle.career.application.dto.request.CreateAdminsCareerRequestDto;
import com.inglo.giggle.career.application.dto.request.UpdateAdminsCareerRequestDto;
import com.inglo.giggle.career.application.usecase.CreateAdminsCareerUseCase;
import com.inglo.giggle.career.application.usecase.UpdateAdminsCareerUseCase;
import com.inglo.giggle.core.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class CareerAdminsCommandV1Controller {

    private final CreateAdminsCareerUseCase createAdminsCareerUseCase;
    private final UpdateAdminsCareerUseCase updateAdminsCareerUseCase;

    /**
     * 14.5 (관리자) 커리어 추가히기
     */
    @PostMapping(value = "/admins/careers", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseDto<Void> createCareer(
            @RequestPart(value = "image", required = false) List<MultipartFile> image,
            @Valid @RequestPart(value = "body") CreateAdminsCareerRequestDto requestDto
    ) {
        createAdminsCareerUseCase.execute(
                image,
                requestDto
        );

        return ResponseDto.created(null);
    }

    /**
     * 14.9 (관리자) 커리어 수정하기
     */
    @PutMapping(value = "/admins/careers/{careerId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseDto<Void> updateCareer(
            @PathVariable(value = "careerId") Long careerId,
            @RequestPart(value = "image", required = false) List<MultipartFile> images,
            @Valid @RequestPart(value = "body") UpdateAdminsCareerRequestDto requestDto
    ) {
        updateAdminsCareerUseCase.execute(
                careerId,
                images,
                requestDto
        );

        return ResponseDto.ok(null);
    }
}
