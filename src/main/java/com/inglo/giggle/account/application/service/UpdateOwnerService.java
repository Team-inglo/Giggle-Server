package com.inglo.giggle.account.application.service;

import com.inglo.giggle.account.application.dto.request.UpdateOwnerRequestDto;
import com.inglo.giggle.account.application.usecase.UpdateOwnerUseCase;
import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.domain.service.OwnerService;
import com.inglo.giggle.account.repository.OwnerRepository;
import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.address.domain.service.AddressService;
import com.inglo.giggle.core.type.EImageType;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.AccountRepository;
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
    private final OwnerService ownerService;
    private final AccountService accountService;
    private final AddressService addressService;
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
            account = accountService.updateProfileImgUrl(account, iconImgUrl);
        }

        // 기본 이미지로 변경이면
        if (requestDto.isIconImgChanged() && image == null) {

            // 기본 이미지 URL 가져오기
            String iconImgUrl = s3Util.getOwnerDefaultImgUrl();

            // 아이콘 이미지 URL 업데이트
            account = accountService.updateProfileImgUrl(account, iconImgUrl);
        }

        // 주소 정보 업데이트
        Address address = addressService.updateAddress(
                owner.getAddress(),
                requestDto.address().addressName(),
                requestDto.address().region1DepthName(),
                requestDto.address().region2DepthName(),
                requestDto.address().region3DepthName(),
                requestDto.address().region4DepthName(),
                requestDto.address().addressDetail(),
                requestDto.address().latitude(),
                requestDto.address().longitude()
        );

        // 고용주 정보 업데이트
        owner = ownerService.updateOwner(owner, requestDto, address);
        ownerRepository.save(owner);

        // phoneNumber 업데이트
        account = accountService.updatePhoneNumber(account, requestDto.ownerInfo().phoneNumber());
        accountRepository.save(account);
    }

}
