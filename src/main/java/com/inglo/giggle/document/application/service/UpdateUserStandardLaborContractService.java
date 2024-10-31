package com.inglo.giggle.document.application.service;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.address.domain.service.AddressService;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.dto.request.UpdateUserStandardLaborContractRequestDto;
import com.inglo.giggle.document.application.usecase.UpdateUserStandardLaborContractUseCase;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.domain.service.StandardLaborContractService;
import com.inglo.giggle.document.repository.mysql.DocumentRepository;
import com.inglo.giggle.document.repository.mysql.StandardLaborContractRepository;
import com.inglo.giggle.posting.domain.service.UserOwnerJobPostingService;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserStandardLaborContractService implements UpdateUserStandardLaborContractUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final DocumentRepository documentRepository;
    private final UserOwnerJobPostingService userOwnerJobPostingService;
    private final StandardLaborContractRepository standardLaborContractRepository;
    private final StandardLaborContractService standardLaborContractService;
    private final AddressService addressService;

    @Override
    @Transactional
    public void execute(UUID accountId, Long documentId, UpdateUserStandardLaborContractRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 계정 타입 유효성 체크
        accountService.checkUserValidation(account);

        // Document 조회
        Document document = documentRepository.findWithUserOwnerJobPostingById(documentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // UserOwnerJobPosting 유저 유효성 체크
        userOwnerJobPostingService.checkUserUserOwnerJobPostingValidation(document.getUserOwnerJobPosting(), accountId);

        // StandardLaborContract 조회
        StandardLaborContract standardLaborContract = standardLaborContractRepository.findById(documentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // StandardLaborContract 수정 유효성 체크
        standardLaborContractService.checkUpdateOrSubmitUserStandardLaborContractValidation(standardLaborContract);

        // Address 생성
        Address address = addressService.createAddress(
                requestDto.address().addressName(),
                requestDto.address().region1DepthName(),
                requestDto.address().region2DepthName(),
                requestDto.address().region3DepthName(),
                requestDto.address().region4DepthName(),
                requestDto.address().addressDetail(),
                requestDto.address().latitude(),
                requestDto.address().longitude()
        );

        // StandardLaborContract 수정
        StandardLaborContract updatedStandardLaborContract = standardLaborContractService.updateUserStandardLaborContract(
                standardLaborContract,
                requestDto.firstName(),
                requestDto.lastName(),
                address,
                requestDto.phoneNumber(),
                requestDto.signatureBase64()
        );
        standardLaborContractRepository.save(updatedStandardLaborContract);
    }

}
