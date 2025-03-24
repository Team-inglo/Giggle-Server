package com.inglo.giggle.school.application.controller.command;

import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.school.application.dto.request.CreateAdminSchoolRequestDto;
import com.inglo.giggle.school.application.dto.request.UpdateAdminSchoolRequestDto;
import com.inglo.giggle.school.application.usecase.CreateAdminSchoolUseCase;
import com.inglo.giggle.school.application.usecase.UpdateAdminSchoolUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admins/schools")
public class SchoolAdminsCommandV1Controller {

    private final CreateAdminSchoolUseCase createAdminSchoolUseCase;
    private final UpdateAdminSchoolUseCase updateAdminSchoolUseCase;

    @PostMapping("")
    public ResponseDto<Void> createAdminSchool(
            @RequestBody @Valid CreateAdminSchoolRequestDto requestDto
    ) {
        createAdminSchoolUseCase.execute(requestDto);
        return ResponseDto.created(null);
    }

    @PutMapping("/{schoolId}")
    public ResponseDto<Void> updateAdminSchool(
            @PathVariable Long schoolId,
            @RequestBody @Valid UpdateAdminSchoolRequestDto requestDto
    ) {
        updateAdminSchoolUseCase.execute(schoolId, requestDto);
        return ResponseDto.ok(null);
    }

}
