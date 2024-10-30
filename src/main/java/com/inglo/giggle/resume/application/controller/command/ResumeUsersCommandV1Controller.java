package com.inglo.giggle.resume.application.controller.command;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.resume.application.dto.request.*;
import com.inglo.giggle.resume.application.usecase.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users/resumes")
public class ResumeUsersCommandV1Controller {
    private final CreateUserWorkExperienceUseCase createUserWorkExperienceUseCase;
    private final CreateUserEducationUseCase createUserEducationUseCase;
    private final CreateUserAdditionalLanguageSkillUseCase createUserAdditionalLanguageSkillUseCase;
    private final UpdateUserIntroductionUseCase updateUserIntroductionUseCase;
    private final UpdateUserWorkExperienceUseCase updateUserWorkExperienceUseCase;
    private final UpdateUserEducationUseCase updateUserEducationUseCase;

    /**
     * 7.5 (유학생) 경력 생성하기
     */
    @PostMapping("/work-experiences")
    public ResponseDto<Void> createUserWorkExperience(
            @AccountID UUID accountId,
            @RequestBody @Valid CreateUserWorkExperienceRequestDto requestDto
    ) {
        createUserWorkExperienceUseCase.execute(accountId, requestDto);
        return ResponseDto.created(null);
    }

    /**
     * 7.6 (유학생) 학력 생성하기
     */
    @PostMapping("/educations")
    public ResponseDto<Void> createUserEducation(
            @AccountID UUID accountId,
            @RequestBody @Valid CreateUserEducationRequestDto requestDto
    ) {
        createUserEducationUseCase.execute(accountId, requestDto);
        return ResponseDto.created(null);
    }

    /**
     * 7.7 (유학생) 언어-ETC 생성하기
     */
    @PostMapping("/additional-languages")
    public ResponseDto<Void> createUserAdditionalLanguageSkill(
            @AccountID UUID accountId,
            @RequestBody @Valid CreateUserAdditionalLanguageSkillRequestDto requestDto
    ) {
        createUserAdditionalLanguageSkillUseCase.execute(accountId, requestDto);
        return ResponseDto.created(null);
    }

    /**
     * 7.8 (유학생) 자기소개 수정하기
     */
    @PatchMapping("/introduction")
    public ResponseDto<Void> updateUserIntroduction(
            @AccountID UUID accountId,
            @RequestBody @Valid UpdateUserIntroductionRequestDto requestDto
    ) {
        updateUserIntroductionUseCase.execute(accountId, requestDto);
        return ResponseDto.ok(null);
    }

    /**
     * 7.9 (유학생) 경력 수정하기
     */
    @PatchMapping("/work-experiences/{id}")
    public ResponseDto<Void> updateUserWorkExperience(
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserWorkExperienceRequestDto requestDto
    ) {
        updateUserWorkExperienceUseCase.execute(id, requestDto);
        return ResponseDto.ok(null);
    }

    /**
     * 7.10 (유학생) 학력 수정하기
     */
    @PatchMapping("/educations/{id}")
    public ResponseDto<Void> updateUserEducation(
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserEducationRequestDto requestDto
    ) {
        updateUserEducationUseCase.execute(id, requestDto);
        return ResponseDto.ok(null);
    }
}
