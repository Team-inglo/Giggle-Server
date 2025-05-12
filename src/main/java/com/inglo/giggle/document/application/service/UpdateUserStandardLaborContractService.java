package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.domain.Address;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.port.in.command.UpdateUserStandardLaborContractCommand;
import com.inglo.giggle.document.application.port.in.usecase.UpdateUserStandardLaborContractUseCase;
import com.inglo.giggle.document.application.port.out.LoadDocumentPort;
import com.inglo.giggle.document.application.port.out.UpdateStandardLaborContractPort;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.security.account.application.port.in.query.ReadAccountRoleQuery;
import com.inglo.giggle.security.account.application.port.in.result.ReadAccountRoleResult;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateUserStandardLaborContractService implements UpdateUserStandardLaborContractUseCase {

    private final LoadDocumentPort loadDocumentPort;
    private final UpdateStandardLaborContractPort updateStandardLaborContractPort;

    private final ReadAccountRoleQuery readAccountRoleQuery;

    @Override
    @Transactional
    public void execute(UpdateUserStandardLaborContractCommand command) {

        // Account 조회
        ReadAccountRoleResult readAccountRoleResult = readAccountRoleQuery.execute(command.getAccountId());

        // 계정 타입 유효성 체크
        checkUserValidation(readAccountRoleResult.getRole());

        // Document 조회
        Document document = loadDocumentPort.loadAllDocumentOrElseThrow(command.getDocumentId());

        //TODO: UOJP 합치기
//        // UserOwnerJobPosting 정보 조회
//        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findByDocumentOrElseThrow(document);
//
//        // UserOwnerJobPosting 유저 유효성 체크
//        userOwnerJobPosting.checkUserUserOwnerJobPostingValidation(accountId);

        // StandardLaborContract 조회
        StandardLaborContract standardLaborContract = (StandardLaborContract) document;

        // StandardLaborContract 수정 유효성 체크
        standardLaborContract.checkUpdateOrSubmitUserStandardLaborContractValidation();

        // Address 생성
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

        // StandardLaborContract 수정
        standardLaborContract.updateByUser(
                command.getFirstName(),
                command.getLastName(),
                address,
                command.getPhoneNumber(),
                command.getSignatureBase64()
        );

        updateStandardLaborContractPort.updateStandardLaborContract(standardLaborContract);
    }

    /* ---------------------------------------------------------------------------------------------------------------*
     * -------                                       private method                                            -------*
     * -------------------------------------------------------------------------------------------------------------- */
    private void checkUserValidation(ESecurityRole role) {
        // 계정 타입이 USER가 아닐 경우 예외처리
        if (role != ESecurityRole.USER) {
            throw new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE);
        }
    }

}
