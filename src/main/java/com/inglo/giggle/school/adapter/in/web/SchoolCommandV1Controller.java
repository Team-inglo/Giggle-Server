package com.inglo.giggle.school.adapter.in.web;

import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.school.adapter.in.web.dto.CreateAdminSchoolRequestDto;
import com.inglo.giggle.school.adapter.in.web.dto.UpdateAdminSchoolRequestDto;
import com.inglo.giggle.school.application.port.in.command.CreateAdminSchoolCommand;
import com.inglo.giggle.school.application.port.in.command.DeleteAdminSchoolCommand;
import com.inglo.giggle.school.application.port.in.command.UpdateAdminSchoolCommand;
import com.inglo.giggle.school.application.port.in.usecase.CreateAdminSchoolUseCase;
import com.inglo.giggle.school.application.port.in.usecase.DeleteAdminSchoolUseCase;
import com.inglo.giggle.school.application.port.in.usecase.UpdateAdminSchoolUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admins/schools")
public class SchoolCommandV1Controller {

    private final CreateAdminSchoolUseCase createAdminSchoolUseCase;
    private final UpdateAdminSchoolUseCase updateAdminSchoolUseCase;

    /**
     * 9.5 (어드민) 학교 생성하기
     */
    @PostMapping("")
    public ResponseDto<Void> createAdminSchool(
            @RequestBody @Valid CreateAdminSchoolRequestDto requestDto
    ) {
        CreateAdminSchoolCommand command = new CreateAdminSchoolCommand(
                requestDto.schoolName(),
                requestDto.schoolPhoneNumber(),
                requestDto.isMetropolitan(),
                requestDto.instituteName(),
                requestDto.coordinatorName(),
                requestDto.coordinatorPhoneNumber(),
                requestDto.address()
        );
        createAdminSchoolUseCase.execute(command);
        return ResponseDto.created(null);
    }

    /**
     * 9.6 (어드민) 학교 수정하기
     */
    @PutMapping("/{schoolId}")
    public ResponseDto<Void> updateAdminSchool(
            @PathVariable Long schoolId,
            @RequestBody @Valid UpdateAdminSchoolRequestDto requestDto
    ) {
        UpdateAdminSchoolCommand command = new UpdateAdminSchoolCommand(
                schoolId,
                requestDto.schoolName(),
                requestDto.schoolPhoneNumber(),
                requestDto.isMetropolitan(),
                requestDto.instituteName(),
                requestDto.coordinatorName(),
                requestDto.coordinatorPhoneNumber(),
                requestDto.address()
        );
        updateAdminSchoolUseCase.execute(command);
        return ResponseDto.ok(null);
    }

    private final DeleteAdminSchoolUseCase deleteAdminSchoolUseCase;

    /**
     * 9.7 (어드민) 학교 삭제하기
     */
    @DeleteMapping("/{schoolId}")
    public ResponseDto<Void> deleteAdminSchool(
            @PathVariable Long schoolId
    ) {
        DeleteAdminSchoolCommand command = new DeleteAdminSchoolCommand(
                schoolId
        );
        deleteAdminSchoolUseCase.execute(command);
        return ResponseDto.ok(null);
    }
}
