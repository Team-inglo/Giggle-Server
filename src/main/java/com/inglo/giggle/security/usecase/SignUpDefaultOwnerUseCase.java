package com.inglo.giggle.security.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.security.dto.request.SignUpDefaultOwnerRequestDto;
import com.inglo.giggle.security.dto.response.DefaultJsonWebTokenDto;
import org.springframework.web.multipart.MultipartFile;
@UseCase
public interface SignUpDefaultOwnerUseCase {

    /**
     * 점주 로그인 전용 회원가입 유스케이스
     * @param requestDto 점주 회원가입 요청 DTO With Token
     * @return DefaultJsonWebTokenDto
     */
    DefaultJsonWebTokenDto execute(SignUpDefaultOwnerRequestDto requestDto, MultipartFile file);
}
