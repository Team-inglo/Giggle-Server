package com.inglo.giggle.user.application.service;

import com.inglo.giggle.core.domain.Address;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.type.EImageType;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.user.application.port.in.command.UpdateUserSelfCommand;
import com.inglo.giggle.user.application.port.in.usecase.UpdateUserSelfUseCase;
import com.inglo.giggle.user.application.port.out.LoadUserPort;
import com.inglo.giggle.user.application.port.out.UpdateUserPort;
import com.inglo.giggle.user.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserSelfService implements UpdateUserSelfUseCase {

    private final LoadUserPort loadUserPort;
    private final UpdateUserPort updateUserPort;
    private final S3Util s3Util;

    @Override
    @Transactional
    public void execute(UpdateUserSelfCommand command) {

        // 유저 정보 조회
        User user = loadUserPort.loadUser(command.getAccountId());

        // 프로필 이미지가 변경됐다면
        if (command.getIsProfileImgChanged() && command.getImage() != null) {

            // 프로필 이미지 업로드
            String profileImgUrl = s3Util.uploadImageFile(command.getImage(), user.getEmail(), EImageType.USER_PROFILE_IMG);

            // 프로필 이미지 URL 업데이트
            user.updateProfileImgUrl(profileImgUrl);
        }

        // 기본 이미지로 변경이면
        if (command.getIsProfileImgChanged() && command.getImage() == null) {

            // 기본 이미지 URL 가져오기
            String profileImgUrl = s3Util.getUserDefaultImgUrl();

            // 프로필 이미지 URL 업데이트
            user.updateProfileImgUrl(profileImgUrl);
        }

        // 주소 정보 업데이트
        Address address = Address.builder()
                .addressName(command.getAddress().addressName())
                .addressDetail(command.getAddress().addressDetail())
                .region1DepthName(command.getAddress().region1DepthName())
                .region2DepthName(command.getAddress().region2DepthName())
                .region3DepthName(command.getAddress().region3DepthName())
                .region4DepthName(command.getAddress().region4DepthName())
                .latitude(command.getAddress().latitude())
                .longitude(command.getAddress().longitude())
                .build();

        // 유저 정보 업데이트
        user.updateSelf(
                command.getPhoneNumber(),
                command.getFirstName(),
                command.getLastName(),
                EGender.fromString(command.getGender()),
                command.getNationality(),
                EVisa.fromString(command.getVisa()),
                command.getBirth(),
                address
        );

        updateUserPort.updateUser(user);
    }

}
