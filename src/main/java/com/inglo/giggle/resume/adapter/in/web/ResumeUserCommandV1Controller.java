package com.inglo.giggle.resume.adapter.in.web;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.resume.adapter.in.web.dto.CreateUserAdditionalLanguageSkillRequestDto;
import com.inglo.giggle.resume.adapter.in.web.dto.CreateUserEducationRequestDto;
import com.inglo.giggle.resume.adapter.in.web.dto.UpdateUserAdditionalLanguageSkillRequestDto;
import com.inglo.giggle.resume.adapter.in.web.dto.UpdateUserEducationRequestDto;
import com.inglo.giggle.resume.adapter.in.web.dto.UpdateUserIntroductionRequestDto;
import com.inglo.giggle.resume.adapter.in.web.dto.UpdateUserSejongInstituteReqeustDto;
import com.inglo.giggle.resume.adapter.in.web.dto.UpdateUserSocialIntegrationProgramReqeustDto;
import com.inglo.giggle.resume.adapter.in.web.dto.UpdateUserTopikReqeustDto;
import com.inglo.giggle.resume.adapter.in.web.dto.UpdateUserWorkExperienceRequestDto;
import com.inglo.giggle.resume.application.port.in.command.CreateAdditionalLanguageCommand;
import com.inglo.giggle.resume.application.port.in.command.CreateEducationCommand;
import com.inglo.giggle.resume.application.port.in.command.CreateWorkExperienceCommand;
import com.inglo.giggle.resume.application.port.in.command.DeleteAdditionalLanguageCommand;
import com.inglo.giggle.resume.application.port.in.command.DeleteEducationCommand;
import com.inglo.giggle.resume.application.port.in.command.DeleteIntroductionCommand;
import com.inglo.giggle.resume.application.port.in.command.DeleteWorkExperienceCommand;
import com.inglo.giggle.resume.application.port.in.command.UpdateAdditionalLanguageCommand;
import com.inglo.giggle.resume.application.port.in.command.UpdateEducationCommand;
import com.inglo.giggle.resume.application.port.in.command.UpdateIntroductionCommand;
import com.inglo.giggle.resume.application.port.in.command.UpdateSejongInstituteCommand;
import com.inglo.giggle.resume.application.port.in.command.UpdateSocialIntegrationProgramCommand;
import com.inglo.giggle.resume.application.port.in.command.UpdateTopikCommand;
import com.inglo.giggle.resume.application.port.in.command.UpdateWorkExperienceCommand;
import com.inglo.giggle.resume.application.port.in.usecase.CreateUserAdditionalLanguageUseCase;
import com.inglo.giggle.resume.application.port.in.usecase.CreateUserEducationUseCase;
import com.inglo.giggle.resume.application.port.in.usecase.CreateUserWorkExperienceUseCase;
import com.inglo.giggle.resume.adapter.in.web.dto.CreateUserWorkExperienceRequestDto;
import com.inglo.giggle.resume.application.port.in.usecase.DeleteUserAdditionalLanguageUseCase;
import com.inglo.giggle.resume.application.port.in.usecase.DeleteUserEducationUseCase;
import com.inglo.giggle.resume.application.port.in.usecase.DeleteUserIntroductionUseCase;
import com.inglo.giggle.resume.application.port.in.usecase.DeleteUserWorkExperienceUseCase;
import com.inglo.giggle.resume.application.port.in.usecase.UpdateUserAdditionalLanguageUseCase;
import com.inglo.giggle.resume.application.port.in.usecase.UpdateUserEducationUseCase;
import com.inglo.giggle.resume.application.port.in.usecase.UpdateUserIntroductionUseCase;
import com.inglo.giggle.resume.application.port.in.usecase.UpdateUserSejongInstituteUseCase;
import com.inglo.giggle.resume.application.port.in.usecase.UpdateUserSocialIntegrationProgramUseCase;
import com.inglo.giggle.resume.application.port.in.usecase.UpdateUserTopikUseCase;
import com.inglo.giggle.resume.application.port.in.usecase.UpdateUserWorkExperienceUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users/resumes")
public class ResumeUserCommandV1Controller {

    private final CreateUserWorkExperienceUseCase createUserWorkExperienceUseCase;
    private final CreateUserEducationUseCase createUserEducationUseCase;
    private final CreateUserAdditionalLanguageUseCase createUserAdditionalLanguageUseCase;
    private final UpdateUserIntroductionUseCase updateUserIntroductionUseCase;
    private final UpdateUserWorkExperienceUseCase updateUserWorkExperienceUseCase;
    private final UpdateUserEducationUseCase updateUserEducationUseCase;
    private final UpdateUserTopikUseCase updateUserTopikUseCase;
    private final UpdateUserSocialIntegrationProgramUseCase updateUserSocialIntegrationProgramUseCase;
    private final UpdateUserSejongInstituteUseCase updateUserSejongInstituteUseCase;
    private final UpdateUserAdditionalLanguageUseCase updateUserAdditionalLanguageUseCase;
    private final DeleteUserIntroductionUseCase deleteUserIntroductionUseCase;
    private final DeleteUserWorkExperienceUseCase deleteUserWorkExperienceUseCase;
    private final DeleteUserEducationUseCase deleteUserEducationUseCase;
    private final DeleteUserAdditionalLanguageUseCase deleteUserAdditionalLanguageUseCase;

    /**
     * 7.5 (유학생) 경력 생성하기
     */
    @PostMapping("/work-experiences")
    public ResponseDto<Void> createUserWorkExperience(
            @AccountID UUID accountId,
            @RequestBody @Valid CreateUserWorkExperienceRequestDto requestDto
    ) {
        CreateWorkExperienceCommand command = new CreateWorkExperienceCommand(
                accountId,
                requestDto.title(),
                requestDto.workplace(),
                requestDto.startDate(),
                requestDto.endDate(),
                requestDto.description()
        );

        createUserWorkExperienceUseCase.execute(command);
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
        CreateEducationCommand command = new CreateEducationCommand(
                accountId,
                requestDto.educationLevel(),
                requestDto.schoolId(),
                requestDto.major(),
                requestDto.gpa(),
                requestDto.startDate(),
                requestDto.endDate(),
                requestDto.grade()
        );
        createUserEducationUseCase.execute(command);
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

        CreateAdditionalLanguageCommand command = new CreateAdditionalLanguageCommand(
                accountId,
                requestDto.languageName(),
                requestDto.level()
        );
        createUserAdditionalLanguageUseCase.execute(command);
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

        UpdateIntroductionCommand command = new UpdateIntroductionCommand(
                accountId,
                requestDto.introduction()
        );

        updateUserIntroductionUseCase.execute(command);
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

        UpdateWorkExperienceCommand command = new UpdateWorkExperienceCommand(
                accountId,
                id,
                requestDto.title(),
                requestDto.workplace(),
                requestDto.startDate(),
                requestDto.endDate(),
                requestDto.description()
        );
        updateUserWorkExperienceUseCase.execute(command);
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

        UpdateEducationCommand command = new UpdateEducationCommand(
                accountId,
                id,
                requestDto.educationLevel(),
                requestDto.schoolId(),
                requestDto.major(),
                requestDto.gpa(),
                requestDto.startDate(),
                requestDto.endDate(),
                requestDto.grade()
        );

        updateUserEducationUseCase.execute(command);
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
        UpdateTopikCommand command = new UpdateTopikCommand(
                accountId,
                requestDto.level()
        );
        updateUserTopikUseCase.execute(command);
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

        UpdateSocialIntegrationProgramCommand command = new UpdateSocialIntegrationProgramCommand(
                accountId,
                requestDto.level()
        );

        updateUserSocialIntegrationProgramUseCase.execute(command);
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

        UpdateSejongInstituteCommand command = new UpdateSejongInstituteCommand(
                accountId,
                requestDto.level()
        );
        updateUserSejongInstituteUseCase.execute(command);
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

        UpdateAdditionalLanguageCommand command = new UpdateAdditionalLanguageCommand(
                accountId,
                id,
                requestDto.languageName(),
                requestDto.level()
        );
        updateUserAdditionalLanguageUseCase.execute(command);
        return ResponseDto.ok(null);
    }

    /**
     * 7.15 (유학생) 자기소개 삭제하기
     */
    @DeleteMapping("/introduction")
    public ResponseDto<Void> deleteUserIntroduction(
            @AccountID UUID accountId
    ) {
        DeleteIntroductionCommand command = new DeleteIntroductionCommand(accountId);
        deleteUserIntroductionUseCase.execute(command);
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
        DeleteWorkExperienceCommand command = new DeleteWorkExperienceCommand(accountId, id);
        deleteUserWorkExperienceUseCase.execute(command);
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
        DeleteEducationCommand command = new DeleteEducationCommand(accountId, id);
        deleteUserEducationUseCase.execute(command);
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
        DeleteAdditionalLanguageCommand command = new DeleteAdditionalLanguageCommand(accountId, id);
        deleteUserAdditionalLanguageUseCase.execute(command);
        return ResponseDto.ok(null);
    }
}
