package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.domain.Address;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.port.in.command.CreateUserStandardLaborContractCommand;
import com.inglo.giggle.document.application.port.in.usecase.CreateUserStandardLaborContractUseCase;
import com.inglo.giggle.document.application.port.out.CreateStandardLaborContractPort;
import com.inglo.giggle.document.application.port.out.LoadStandardLaborContractPort;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.security.account.application.port.in.query.ReadAccountRoleQuery;
import com.inglo.giggle.security.account.application.port.in.result.ReadAccountRoleResult;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateUserStandardLaborContractService implements CreateUserStandardLaborContractUseCase {

    private final LoadStandardLaborContractPort loadStandardLaborContractPort;
    private final CreateStandardLaborContractPort createStandardLaborContractPort;

    private final ReadAccountRoleQuery readAccountRoleQuery;

    @Override
    @Transactional
    public void execute(CreateUserStandardLaborContractCommand command) {

        // Account 조회
        ReadAccountRoleResult readAccountRoleResult = readAccountRoleQuery.execute(command.getAccountId());

        // 계정 타입 유효성 체크
        checkUserValidation(readAccountRoleResult.getRole());

        //TODO: UOJP 합치기
//        // UserOwnerJobPosting 조회
//        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithOwnerAndUserJobPostingByIdOrElseThrow(userOwnerJobPostingId);

        // 해당 UserOwnerJobPosting 과 연결된 StandardLaborContract 이 이미 존재하는지 확인
        if (loadStandardLaborContractPort.loadStandardLaborContractByUserOwnerJobPostingIdOrElseNull(command.getUserOwnerJobPostingId()) != null) {
            throw new CommonException(ErrorCode.ALREADY_EXIST_RESOURCE);
        }

//        // UserOwnerJobPosting 유저 유효성 체크
//        userOwnerJobPosting.checkUserUserOwnerJobPostingValidation(accountId);

        // Address 생성
        Address address = Address.builder()
                .addressName(command.getAddress().addressName())
                .region1DepthName(command.getAddress().region1DepthName())
                .region2DepthName(command.getAddress().region2DepthName())
                .region3DepthName(command.getAddress().region3DepthName())
                .region4DepthName(command.getAddress().region4DepthName())
                .addressDetail(command.getAddress().addressDetail())
                .latitude(command.getAddress().latitude())
                .longitude(command.getAddress().longitude())
                .build();

        // StandardLaborContract 생성
        StandardLaborContract standardLaborContract = StandardLaborContract.builder()
                .employeeFirstName(command.getFirstName())
                .employeeLastName(command.getLastName())
                .employeeAddress(address)
                .employeePhoneNumber(command.getPhoneNumber())
                .employeeSignatureBase64(command.getSignatureBase64())
                .build();

        createStandardLaborContractPort.createStandardLaborContract(standardLaborContract);
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
