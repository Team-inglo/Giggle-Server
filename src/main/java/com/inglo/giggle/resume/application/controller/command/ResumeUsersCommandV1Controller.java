package com.inglo.giggle.resume.application.controller.command;

import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.resume.application.dto.request.CreateUserWorkExperienceRequestDto;
import com.inglo.giggle.resume.application.usecase.CreateUserWorkExperienceUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users/resumes")
public class ResumeUsersCommandV1Controller {
    private final CreateUserWorkExperienceUseCase createUserWorkExperienceUseCase;

    @PostMapping("/work-experiences")
    public ResponseDto<Void> createUserWorkExperience(
            @RequestBody @Valid CreateUserWorkExperienceRequestDto requestDto
            ) {
        createUserWorkExperienceUseCase.execute(requestDto);
        return ResponseDto.created(null);
    }
}
