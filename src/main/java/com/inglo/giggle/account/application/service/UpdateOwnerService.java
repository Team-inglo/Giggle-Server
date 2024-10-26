package com.inglo.giggle.account.application.service;

import com.inglo.giggle.account.application.dto.request.UpdateOwnerRequestDto;
import com.inglo.giggle.account.application.usecase.UpdateOwnerUseCase;
import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.domain.service.OwnerService;
import com.inglo.giggle.account.repository.mysql.OwnerRepository;
import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.address.domain.service.AddressService;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.utility.ImageUtil;
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
public class UpdateOwnerService implements UpdateOwnerUseCase {

    private final AccountRepository accountRepository;
    private final OwnerRepository ownerRepository;
    private final OwnerService ownerService;
    private final AccountService accountService;
    private final AddressService addressService;

    private final ImageUtil imageUtil;

    @Override
    @Transactional
    public void execute(UUID accountId, UpdateOwnerRequestDto requestDto, MultipartFile image) {
        // Account 조회
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 고용주 조회
        Owner owner = ownerRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 아이콘 이미지가 변경됐다면
        if (requestDto.isIconImgChanged() && image != null) {

            // 아이콘 이미지 업로드
            String iconImgUrl = imageUtil.uploadOwnerIconImageFile(image, account.getSerialId());

            // 아이콘 이미지 URL 업데이트
            account = accountService.updateProfileImgUrl(account, iconImgUrl);
        }

        // 기본 이미지로 변경이면
        if (requestDto.isIconImgChanged() && image == null) {

            // 기본 이미지 URL 가져오기
            String iconImgUrl = imageUtil.getOwnerDefaultImgUrl();

            // 아이콘 이미지 URL 업데이트
            account = accountService.updateProfileImgUrl(account, iconImgUrl);
        }

        // 주소 정보 업데이트
        Address address = addressService.updateAddress(owner.getAddress(), requestDto.address());

        // 고용주 정보 업데이트
        owner = ownerService.updateOwner(owner, requestDto, address);
        ownerRepository.save(owner);

        // phoneNumber 업데이트
        account = accountService.updatePhoneNumber(account, requestDto.ownerInfo().phoneNumber());
        accountRepository.save(account);
    }
}
