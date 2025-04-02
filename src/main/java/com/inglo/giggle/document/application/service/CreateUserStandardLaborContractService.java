package com.inglo.giggle.document.application.service;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.usecase.CreateUserStandardLaborContractUseCase;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.persistence.repository.StandardLaborContractRepository;
import com.inglo.giggle.document.presentation.dto.request.CreateUserStandardLaborContractRequestDto;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserStandardLaborContractService implements CreateUserStandardLaborContractUseCase {

    private final AccountRepository accountRepository;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final StandardLaborContractRepository standardLaborContractRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, Long userOwnerJobPostingId, CreateUserStandardLaborContractRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        account.checkUserValidation();

        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithOwnerAndUserJobPostingByIdOrElseThrow(userOwnerJobPostingId);

        // 해당 UserOwnerJobPosting 과 연결된 StandardLaborContract 이 이미 존재하는지 확인
        if (standardLaborContractRepository.findByUserOwnerJobPostingIdOrElseNull(userOwnerJobPostingId) != null) {
            throw new CommonException(ErrorCode.ALREADY_EXIST_RESOURCE);
        }

        // UserOwnerJobPosting 유저 유효성 체크
        userOwnerJobPosting.checkUserUserOwnerJobPostingValidation(accountId);

        // Address 생성
        Address address = Address.builder()
                .addressName(requestDto.address().addressName())
                .region1DepthName(requestDto.address().region1DepthName())
                .region2DepthName(requestDto.address().region2DepthName())
                .region3DepthName(requestDto.address().region3DepthName())
                .region4DepthName(requestDto.address().region4DepthName())
                .addressDetail(requestDto.address().addressDetail())
                .latitude(requestDto.address().latitude())
                .longitude(requestDto.address().longitude())
                .build();

        // StandardLaborContract 생성
        StandardLaborContract standardLaborContract = StandardLaborContract.builder()
                .employeeFirstName(requestDto.firstName())
                .employeeLastName(requestDto.lastName())
                .employeeAddress(address)
                .employeePhoneNumber(requestDto.phoneNumber())
                .employeeSignatureBase64(requestDto.signatureBase64())
                .build();

        standardLaborContractRepository.save(standardLaborContract);
    }

}
