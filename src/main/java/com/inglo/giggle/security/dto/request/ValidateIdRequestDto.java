package com.inglo.giggle.security.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ValidateIdRequestDto(
        @JsonProperty("id")
        @NotBlank(message = "아이디를 입력해주세요.")
        @Pattern(regexp = "^[A-Za-z0-9]{1,320}$", message = "아이디는 1글자 이상 320자 이하의 영어 대소문자, 숫자로 입력해주세요.")
        String id
) {
}