package com.inglo.giggle.account.application.service;

import com.inglo.giggle.account.application.usecase.UpdateUserUseCase;
import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.account.persistence.repository.UserRepository;
import com.inglo.giggle.account.presentation.dto.request.UpdateUserRequestDto;
import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.type.EImageType;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
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
    private final S3Util s3Util;

    @Override
    @Transactional
    public void execute(UUID accountId, UpdateUserRequestDto requestDto, MultipartFile image) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 유저 정보 조회
        User user = userRepository.findByIdOrElseThrow(accountId);

        // 프로필 이미지가 변경됐다면
        if (requestDto.isProfileImgChanged() && image != null) {

            // 프로필 이미지 업로드
            String profileImgUrl = s3Util.uploadImageFile(image, account.getSerialId(), EImageType.USER_PROFILE_IMG);

            // 프로필 이미지 URL 업데이트
            account.updateProfileImgUrl(profileImgUrl);
        }

        // 기본 이미지로 변경이면
        if (requestDto.isProfileImgChanged() && image == null) {

            // 기본 이미지 URL 가져오기
            String profileImgUrl = s3Util.getUserDefaultImgUrl();

            // 프로필 이미지 URL 업데이트
            account.updateProfileImgUrl(profileImgUrl);
        }

        // 주소 정보 업데이트
        Address address = Address.builder()
                .addressName(requestDto.address().addressName())
                .addressDetail(requestDto.address().addressDetail())
                .region1DepthName(requestDto.address().region1DepthName())
                .region2DepthName(requestDto.address().region2DepthName())
                .region3DepthName(requestDto.address().region3DepthName())
                .region4DepthName(requestDto.address().region4DepthName())
                .latitude(requestDto.address().latitude())
                .longitude(requestDto.address().longitude())
                .build();

        // 유저 정보 업데이트
        user.updateSelf(
                requestDto.firstName(),
                requestDto.lastName(),
                EGender.fromString(requestDto.gender()),
                requestDto.nationality(),
                EVisa.fromString(requestDto.visa()),
                requestDto.birth(),
                address
        );

        userRepository.save(user);

        // phoneNumber 업데이트
        account.updatePhoneNumber(requestDto.phoneNumber());
        accountRepository.save(account);
    }

}
