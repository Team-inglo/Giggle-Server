package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.port.in.command.CreateUserPartTimeEmploymentPermitCommand;
import com.inglo.giggle.document.application.port.in.usecase.CreateUserPartTimeEmploymentPermitUseCase;
import com.inglo.giggle.document.application.port.out.CreatePartTimeEmploymentPermitPort;
import com.inglo.giggle.document.application.port.out.LoadPartTimeEmploymentPermitPort;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.type.EEmployeeStatus;
import com.inglo.giggle.security.account.application.port.in.query.ReadAccountRoleQuery;
import com.inglo.giggle.security.account.application.port.in.result.ReadAccountRoleResult;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateUserPartTimeEmploymentPermitService implements CreateUserPartTimeEmploymentPermitUseCase {

    private final LoadPartTimeEmploymentPermitPort loadPartTimeEmploymentPermitPort;
    private final CreatePartTimeEmploymentPermitPort createPartTimeEmploymentPermitPort;

    private final ReadAccountRoleQuery readAccountRoleQuery;

    @Override
    @Transactional
    public void execute(CreateUserPartTimeEmploymentPermitCommand command) {

        // Account 조회
        ReadAccountRoleResult readAccountRoleResult = readAccountRoleQuery.execute(command.getAccountId());

        // 계정 타입 유효성 체크
        checkUserValidation(readAccountRoleResult.getRole());

        //TODO: UOJP 합치기
//        // UserOwnerJobPosting 조회
//        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithOwnerAndUserJobPostingByIdOrElseThrow(userOwnerJobPostingId);

        // 해당 UserOwnerJobPosting 과 연결된 PartTimeEmploymentPermit 이 이미 존재하는지 확인
        if (loadPartTimeEmploymentPermitPort.loadPartTimeEmploymentPermitByUserOwnerJobPostingIdOrElseNull(command.getUserOwnerJobPostingId()) != null) {
            throw new CommonException(ErrorCode.ALREADY_EXIST_RESOURCE);
        }

//        // UserOwnerJobPosting 유저 유효성 체크
//        userOwnerJobPosting.checkUserUserOwnerJobPostingValidation(accountId);

        // PartTimeEmploymentPermit 생성
        PartTimeEmploymentPermit partTimeEmploymentPermit = PartTimeEmploymentPermit.builder()
                .employeeFirstName(command.getFirstName())
                .employeeLastName(command.getLastName())
                .major(command.getMajor())
                .termOfCompletion(command.getTermOfCompletion())
                .employeePhoneNumber(command.getPhoneNumber())
                .employeeEmail(command.getEmail())
                .employeeStatus(EEmployeeStatus.TEMPORARY_SAVE)
                .build();

        createPartTimeEmploymentPermitPort.createPartTimeEmploymentPermit(partTimeEmploymentPermit);
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
