package com.inglo.giggle.owner.application.service;

import com.inglo.giggle.core.domain.Address;
import com.inglo.giggle.core.type.EImageType;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.owner.application.port.in.command.UpdateOwnerCommand;
import com.inglo.giggle.owner.application.port.in.usecase.UpdateOwnerUseCase;
import com.inglo.giggle.owner.application.port.out.LoadOwnerPort;
import com.inglo.giggle.owner.application.port.out.UpdateOwnerPort;
import com.inglo.giggle.owner.domain.Owner;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateOwnerService implements UpdateOwnerUseCase {

    private final LoadOwnerPort loadOwnerPort;
    private final UpdateOwnerPort updateOwnerPort;

    private final S3Util s3Util;

    @Override
    @Transactional
    public void execute(UpdateOwnerCommand command) {

        // 고용주 조회
        Owner owner = loadOwnerPort.loadOwner(command.getAccountId());

        // 아이콘 이미지가 변경됐다면
        if (command.getIsIconImgChanged() && command.getImage()!= null) {

            // 아이콘 이미지 업로드
            String iconImgUrl = s3Util.uploadImageFile(command.getImage(), owner.getEmail(), EImageType.OWNER_PROFILE_IMG);

            // 아이콘 이미지 URL 업데이트
            owner.updateProfileImgUrl(iconImgUrl);
        }

        // 기본 이미지로 변경이면
        if (command.getIsIconImgChanged() && command.getImage() == null) {

            // 기본 이미지 URL 가져오기
            String iconImgUrl = s3Util.getOwnerDefaultImgUrl();

            // 아이콘 이미지 URL 업데이트
            owner.updateProfileImgUrl(iconImgUrl);
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

        // 고용주 정보 업데이트
        owner.updateSelf(
                command.getPhoneNumber(),
                command.getCompanyName(),
                command.getOwnerName(),
                command.getCompanyRegistrationNumber(),
                address
        );

        // 고용주 정보 저장
        updateOwnerPort.updateOwner(owner);
    }

}
