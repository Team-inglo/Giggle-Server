package com.inglo.giggle.security.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ChangePasswordRequestDto(
        @JsonProperty("current_password")
        @NotBlank(message = "현재 비밀번호를 입력해주세요.")
        String currentPassword,
        @JsonProperty("new_password")
        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(
                regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$",
                message = "비밀번호는 8글자 이상 20자 이하로, 영어 대소문자, 숫자, 특수문자 중 3가지를 적어도 하나 이상씩 사용하고, 조합하여 입력해주세요.")
        String newPassword
) {
}
