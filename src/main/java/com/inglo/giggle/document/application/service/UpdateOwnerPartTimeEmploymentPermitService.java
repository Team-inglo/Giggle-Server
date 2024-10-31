package com.inglo.giggle.document.application.service;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.address.domain.service.AddressService;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.dto.request.UpdateOwnerPartTimeEmploymentPermitRequestDto;
import com.inglo.giggle.document.application.usecase.UpdateOwnerPartTimeEmploymentPermitUseCase;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.service.PartTimeEmploymentPermitService;
import com.inglo.giggle.document.repository.mysql.DocumentRepository;
import com.inglo.giggle.document.repository.mysql.PartTimeEmploymentPermitRepository;
import com.inglo.giggle.posting.domain.service.UserOwnerJobPostingService;
import com.inglo.giggle.posting.domain.type.EWorkPeriod;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateOwnerPartTimeEmploymentPermitService implements UpdateOwnerPartTimeEmploymentPermitUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final DocumentRepository documentRepository;
    private final UserOwnerJobPostingService userOwnerJobPostingService;
    private final PartTimeEmploymentPermitRepository partTimeEmploymentPermitRepository;
    private final PartTimeEmploymentPermitService partTimeEmploymentPermitService;
    private final AddressService addressService;

    @Override
    @Transactional
    public void execute(UUID accountId, Long documentId, UpdateOwnerPartTimeEmploymentPermitRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 계정 타입 유효성 체크
        accountService.checkOwnerValidation(account);

        // Document 조회
        Document document = documentRepository.findWithUserOwnerJobPostingById(documentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // UserOwnerJobPosting 고용주 유효성 체크
        userOwnerJobPostingService.checkOwnerUserOwnerJobPostingValidation(document.getUserOwnerJobPosting(), accountId);

        // PartTimeEmploymentPermit 조회
        PartTimeEmploymentPermit partTimeEmploymentPermit = partTimeEmploymentPermitRepository.findById(documentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // PartTimeEmploymentPermit 수정 유효성 체크
        partTimeEmploymentPermitService.checkUpdateOrSubmitOwnerPartTimeEmploymentPermitValidation(partTimeEmploymentPermit);

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

        // PartTimeEmploymentPermit 수정
        PartTimeEmploymentPermit updatedPartTimeEmploymentPermit = partTimeEmploymentPermitService.updateOwnerPartTimeEmploymentPermit(
                partTimeEmploymentPermit,
                requestDto.companyName(),
                requestDto.companyRegistrationNumber(),
                requestDto.jobType(),
                requestDto.name(),
                requestDto.phoneNumber(),
                requestDto.signatureBase64(),
                EWorkPeriod.fromString(requestDto.workPeriod()),
                requestDto.hourlyRate(),
                requestDto.workDaysWeekdays(),
                requestDto.workDaysWeekends(),
                address
        );
        partTimeEmploymentPermitRepository.save(updatedPartTimeEmploymentPermit);
    }

}
