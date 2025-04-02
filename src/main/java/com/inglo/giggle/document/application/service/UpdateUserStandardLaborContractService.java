package com.inglo.giggle.document.application.service;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.document.application.usecase.UpdateUserStandardLaborContractUseCase;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.persistence.repository.DocumentRepository;
import com.inglo.giggle.document.persistence.repository.StandardLaborContractRepository;
import com.inglo.giggle.document.presentation.dto.request.UpdateUserStandardLaborContractRequestDto;
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
public class UpdateUserStandardLaborContractService implements UpdateUserStandardLaborContractUseCase {

    private final AccountRepository accountRepository;
    private final DocumentRepository documentRepository;
    private final StandardLaborContractRepository standardLaborContractRepository;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, Long documentId, UpdateUserStandardLaborContractRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        account.checkUserValidation();

        // Document 조회
        Document document = documentRepository.findByIdOrElseThrow(documentId);

        // UserOwnerJobPosting 정보 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findByDocumentOrElseThrow(document);

        // UserOwnerJobPosting 유저 유효성 체크
        userOwnerJobPosting.checkUserUserOwnerJobPostingValidation(accountId);

        // StandardLaborContract 조회
        StandardLaborContract standardLaborContract = standardLaborContractRepository.findByIdOrElseThrow(documentId);

        // StandardLaborContract 수정 유효성 체크
        standardLaborContract.checkUpdateOrSubmitUserStandardLaborContractValidation();

        // Address 생성
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

        // StandardLaborContract 수정
        standardLaborContract.updateByUser(
                requestDto.firstName(),
                requestDto.lastName(),
                address,
                requestDto.phoneNumber(),
                requestDto.signatureBase64()
        );

        standardLaborContractRepository.save(standardLaborContract);
    }

}
