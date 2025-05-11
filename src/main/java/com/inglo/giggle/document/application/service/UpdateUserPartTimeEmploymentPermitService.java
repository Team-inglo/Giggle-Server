package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.port.in.command.UpdateUserPartTimeEmploymentPermitCommand;
import com.inglo.giggle.document.application.port.in.usecase.UpdateUserPartTimeEmploymentPermitUseCase;
import com.inglo.giggle.document.application.port.out.LoadDocumentPort;
import com.inglo.giggle.document.application.port.out.UpdatePartTimeEmploymentPermitPort;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.security.account.application.port.in.query.ReadAccountRoleQuery;
import com.inglo.giggle.security.account.application.port.in.result.ReadAccountRoleResult;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateUserPartTimeEmploymentPermitService implements UpdateUserPartTimeEmploymentPermitUseCase {

    private final LoadDocumentPort loadDocumentPort;
    private final UpdatePartTimeEmploymentPermitPort updatePartTimeEmploymentPermitPort;

    private final ReadAccountRoleQuery readAccountRoleQuery;

    @Override
    @Transactional
    public void execute(UpdateUserPartTimeEmploymentPermitCommand command) {

        // Account 조회
        ReadAccountRoleResult readAccountRoleResult = readAccountRoleQuery.execute(command.getAccountId());

        // 계정 타입 유효성 체크
        checkUserValidation(readAccountRoleResult.getRole());

        // Document 조회
        Document document = loadDocumentPort.loadDocument(command.getDocumentId());

        //TODO: UOJP 합치기
//        // UserOwnerJobPosting 정보 조회
//        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findByDocumentOrElseThrow(document);
//
//        // UserOwnerJobPosting 유저 유효성 체크
//        userOwnerJobPosting.checkUserUserOwnerJobPostingValidation(accountId);

        // PartTimeEmploymentPermit 조회
        PartTimeEmploymentPermit partTimeEmploymentPermit = (PartTimeEmploymentPermit) document;

        // PartTimeEmploymentPermit 수정 유효성 체크
        partTimeEmploymentPermit.checkUpdateOrSubmitUserPartTimeEmploymentPermitValidation();

        // PartTimeEmploymentPermit 수정
        partTimeEmploymentPermit.updateByUser(
                command.getFirstName(),
                command.getLastName(),
                command.getMajor(),
                command.getTermOfCompletion(),
                command.getPhoneNumber(),
                command.getEmail()
        );
        updatePartTimeEmploymentPermitPort.updatePartTimeEmploymentPermit(partTimeEmploymentPermit);
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
