package com.inglo.giggle.account.application.service;

import com.inglo.giggle.account.application.dto.request.UpdateUserRequestDto;
import com.inglo.giggle.account.application.usecase.UpdateUserUseCase;
import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.account.domain.service.UserService;
import com.inglo.giggle.account.repository.mysql.UserRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EImageType;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserService implements UpdateUserUseCase {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final UserService userService;
    private final AccountService accountService;

    private final S3Util s3Util;

    @Override
    @Transactional
    public void execute(UUID accountId, UpdateUserRequestDto requestDto, MultipartFile image) {
        // Account 조회
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 유저 정보 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 프로필 이미지가 변경됐다면
        if (requestDto.isProfileImgChanged() && image != null) {

            // 프로필 이미지 업로드
            String profileImgUrl = s3Util.uploadImageFile(image, account.getSerialId(), EImageType.USER_PROFILE_IMG);

            // 프로필 이미지 URL 업데이트
            account = accountService.updateProfileImgUrl(account, profileImgUrl);
        }

        // 기본 이미지로 변경이면
        if (requestDto.isProfileImgChanged() && image == null) {

            // 기본 이미지 URL 가져오기
            String profileImgUrl = s3Util.getUserDefaultImgUrl();

            // 프로필 이미지 URL 업데이트
            account = accountService.updateProfileImgUrl(account, profileImgUrl);
        }

        // 유저 정보 업데이트
        user = userService.updateUser(user, requestDto);
        userRepository.save(user);

        // phoneNumber 업데이트
        account = accountService.updatePhoneNumber(account, requestDto.phoneNumber());
        accountRepository.save(account);
    }
}
