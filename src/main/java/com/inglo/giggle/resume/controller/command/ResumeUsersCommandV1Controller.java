package com.inglo.giggle.resume.controller.command;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.resume.application.dto.request.*;
import com.inglo.giggle.resume.application.usecase.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users/resumes")
public class ResumeUsersCommandV1Controller {
    private final CreateUserWorkExperienceUseCase createUserWorkExperienceUseCase;
    private final CreateUserEducationUseCase createUserEducationUseCase;
    private final CreateUserAdditionalLanguageSkillUseCase createUserAdditionalLanguageSkillUseCase;
    private final UpdateUserResumeUseCase updateUserResumeUseCase;
    private final UpdateUserWorkExperienceUseCase updateUserWorkExperienceUseCase;
    private final UpdateUserEducationUseCase updateUserEducationUseCase;
    private final UpdateUserTopikUseCase updateUserTopikUseCase;
    private final UpdateUserSejongInstituteUseCase updateUserSejongInstituteUseCase;
    private final UpdateUserSocialIntegrationProgramUseCase updateUserSocialIntegrationProgramUseCase;
    private final UpdateUserAdditionalLanguageSkillUseCase updateUserAdditionalLanguageSkillUseCase;
    private final DeleteUserIntroductionUseCase deleteUserIntroductionUseCase;
    private final DeleteUserEducationUseCase deleteUserEducationUseCase;
    private final DeleteUserAdditionalLanguageUseCase deleteUserAdditionalLanguageUseCase;
    private final DeleteUserWorkExperienceUseCase deleteUserWorkExperienceUseCase;
    private final UpdateUserWorkPreferenceUseCase updateUserWorkPreferenceUseCase;
    private final UpdateUserResumeIsPublicUseCase updateUserResumeIsPublicUseCase;

    /**
     * 7.5 (유학생) 경력 생성하기
     */
    @PostMapping("/work-experiences")
    public ResponseDto<Void> createUserWorkExperience(
            @AccountID UUID accountId,
            @RequestBody @Valid CreateUserWorkExperienceRequestDto requestDto
    ) {
        createUserWorkExperienceUseCase.execute(
                accountId,
                requestDto.validate()
        );
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
        createUserEducationUseCase.execute(
                accountId,
                requestDto.validate()
        );
        return ResponseDto.created(null);
    }

    /**
     * 7.7 (유학생) 언어-ETC 생성하기
     */
    @PostMapping("/languages/additional-languages")
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
            @RequestBody @Valid UpdateUserResumeRequestDtoV1 requestDto
    ) {
        updateUserResumeUseCase.execute(accountId, requestDto);
        return ResponseDto.ok(null);
    }

    /**
     * 7.9 (유학생) 경력 수정하기
     */
    @PatchMapping("/work-experiences/{id}")
    public ResponseDto<Void> updateUserWorkExperience(
            @AccountID UUID accountId,
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserWorkExperienceRequestDto requestDto
    ) {
        updateUserWorkExperienceUseCase.execute(
                accountId,
                id,
                requestDto.validate()
        );
        return ResponseDto.ok(null);
    }

    /**
     * 7.10 (유학생) 학력 수정하기
     */
    @PatchMapping("/educations/{id}")
    public ResponseDto<Void> updateUserEducation(
            @AccountID UUID accountId,
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserEducationRequestDto requestDto
    ) {
        updateUserEducationUseCase.execute(
                accountId,
                id,
                requestDto.validate()
        );
        return ResponseDto.ok(null);
    }

    /**
     * 7.11 (유학생) TOPIK 레벨 수정하기
     */
    @PatchMapping("/languages/topik")
    public ResponseDto<Void> updateUserTopik(
            @AccountID UUID accountId,
            @RequestBody @Valid UpdateUserTopikReqeustDto requestDto
    ) {
        updateUserTopikUseCase.execute(accountId, requestDto);
        return ResponseDto.ok(null);
    }

    /**
     * 7.12 (유학생) SOCIAL INTEGRATION PROGRAM 레벨 수정하기
     */
    @PatchMapping("/languages/social-integration-program")
    public ResponseDto<Void> updateUserSocialIntegrationProgram(
            @AccountID UUID accountId,
            @RequestBody @Valid UpdateUserSocialIntegrationProgramReqeustDto requestDto
    ) {
        updateUserSocialIntegrationProgramUseCase.execute(accountId, requestDto);
        return ResponseDto.ok(null);
    }

    /**
     * 7.13 (유학생) SEJONG INSTITUTE 레벨 수정하기
     */
    @PatchMapping("/languages/sejong-institute")
    public ResponseDto<Void> updateUserSejongInstitute(
            @AccountID UUID accountId,
            @RequestBody @Valid UpdateUserSejongInstituteReqeustDto requestDto
    ) {
        updateUserSejongInstituteUseCase.execute(accountId, requestDto);
        return ResponseDto.ok(null);
    }

    /**
     * 7.14 (유학생) 언어-ETC 수정하기
     */
    @PatchMapping("/languages/additional-languages/{id}")
    public ResponseDto<Void> updateUserAdditionalLanguageSkill(
            @AccountID UUID accountId,
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserAdditionalLanguageSkillRequestDto requestDto
    ) {
        updateUserAdditionalLanguageSkillUseCase.execute(accountId, id, requestDto);
        return ResponseDto.ok(null);
    }

    /**
     * 7.15 (유학생) 자기소개 삭제하기
     */
    @DeleteMapping("/introduction")
    public ResponseDto<Void> deleteUserIntroduction(
            @AccountID UUID accountId
    ) {
        deleteUserIntroductionUseCase.execute(accountId);
        return ResponseDto.ok(null);
    }

    /**
     * 7.16 (유학생) 경력 삭제하기
     */
    @DeleteMapping("/work-experiences/{id}")
    public ResponseDto<Void> deleteUserWorkExperience(
            @AccountID UUID accountId,
            @PathVariable Long id
    ) {
        deleteUserWorkExperienceUseCase.execute(accountId, id);
        return ResponseDto.ok(null);
    }

    /**
     * 7.17 (유학생) 학력 삭제하기
     */
    @DeleteMapping("/educations/{id}")
    public ResponseDto<Void> deleteUserEducation(
            @AccountID UUID accountId,
            @PathVariable Long id
    ) {
        deleteUserEducationUseCase.execute(accountId, id);
        return ResponseDto.ok(null);
    }

    /**
     * 7.18 (유학생) 언어-ETC 삭제하기
     */
    @DeleteMapping("/languages/additional-languages/{id}")
    public ResponseDto<Void> deleteUserAdditionalLanguage(
            @AccountID UUID accountId,
            @PathVariable Long id
    ) {
        deleteUserAdditionalLanguageUseCase.execute(accountId, id);
        return ResponseDto.ok(null);
    }

    /**
     * 7.22 (유학생) 희망 근로 조건 수정하기
     */
    @PutMapping("/work-preferences")
    public ResponseDto<Void> updateUserWorkPreference(
            @AccountID UUID accountId,
            @RequestBody @Valid UpdateUserWorkPreferenceRequestDto requestDto
    ) {
        updateUserWorkPreferenceUseCase.execute(accountId, requestDto);
        return ResponseDto.ok(null);
    }

    /**
     * 7.23 (유학생) 이력서 공개/비공개 변경하기
     */
    @PatchMapping("/is-public")
    public ResponseDto<Void> updateUserResumeIsPublic(
            @AccountID UUID accountId,
            @RequestBody @Valid UpdateUserResumeIsPublicRequestDto requestDto
    ) {
        updateUserResumeIsPublicUseCase.execute(
                requestDto.isPublic(),
                accountId
        );
        return ResponseDto.ok(null);
    }

}
