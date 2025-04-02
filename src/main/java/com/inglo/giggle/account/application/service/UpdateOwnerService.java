package com.inglo.giggle.account.application.service;

import com.inglo.giggle.account.application.usecase.UpdateOwnerUseCase;
import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.persistence.repository.OwnerRepository;
import com.inglo.giggle.account.presentation.dto.request.UpdateOwnerRequestDto;
import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.type.EImageType;
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
public class UpdateOwnerService implements UpdateOwnerUseCase {

    private final AccountRepository accountRepository;
    private final OwnerRepository ownerRepository;

    private final S3Util s3Util;

    @Override
    @Transactional
    public void execute(UUID accountId, UpdateOwnerRequestDto requestDto, MultipartFile image) {

        // 계정 정보 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 고용주 조회
        Owner owner = ownerRepository.findByIdOrElseThrow(accountId);

        // 아이콘 이미지가 변경됐다면
        if (requestDto.isIconImgChanged() && image != null) {

            // 아이콘 이미지 업로드
            String iconImgUrl = s3Util.uploadImageFile(image, account.getSerialId(), EImageType.OWNER_PROFILE_IMG);

            // 아이콘 이미지 URL 업데이트
            account.updateProfileImgUrl(iconImgUrl);
        }

        // 기본 이미지로 변경이면
        if (requestDto.isIconImgChanged() && image == null) {

            // 기본 이미지 URL 가져오기
            String iconImgUrl = s3Util.getOwnerDefaultImgUrl();

            // 아이콘 이미지 URL 업데이트
            account.updateProfileImgUrl(iconImgUrl);
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

        // 고용주 정보 업데이트
        owner.updateSelf(
                requestDto.ownerInfo().companyName(),
                requestDto.ownerInfo().ownerName(),
                requestDto.ownerInfo().companyRegistrationNumber(),
                address
        );
        ownerRepository.save(owner);

        // phoneNumber 업데이트
        account.updatePhoneNumber(requestDto.ownerInfo().phoneNumber());
        accountRepository.save(account);
    }

}
