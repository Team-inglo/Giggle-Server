package com.inglo.giggle.termaccount.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.account.application.port.in.query.ReadAccountRoleQuery;
import com.inglo.giggle.security.account.application.port.in.result.ReadAccountRoleResult;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import com.inglo.giggle.term.application.port.in.query.ReadTermsByTypesQuery;
import com.inglo.giggle.term.application.port.in.result.ReadTermsByTypesResult;
import com.inglo.giggle.term.domain.type.ETermType;
import com.inglo.giggle.termaccount.application.port.in.command.CreateTermAccountCommand;
import com.inglo.giggle.termaccount.application.port.in.usecase.CreateTermAccountUseCase;
import com.inglo.giggle.termaccount.application.port.out.CreateTermAccountPort;
import com.inglo.giggle.termaccount.domain.TermAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateTermAccountService implements CreateTermAccountUseCase {

    private final CreateTermAccountPort createTermAccountPort;

    private final ReadTermsByTypesQuery readTermsByTypesQuery;
    private final ReadAccountRoleQuery readAccountRoleQuery;

    @Override
    public void execute(CreateTermAccountCommand command) {

        // 약관 타입 파싱
        List<ETermType> termTypes = command.getTermTypes().stream()
                .map(ETermType::fromString)
                .toList();

        ReadAccountRoleResult accountRoleResult = readAccountRoleQuery.execute(command.getAccountId());

        // Account TermType 검증
        validateAccountTermType(accountRoleResult.getRole(), termTypes);

        // 약관 데이터 조회
        ReadTermsByTypesResult termsResult = readTermsByTypesQuery.execute(termTypes);

        // 약관 동의 생성
        List<TermAccount> termAccounts = termsResult.getTerms().stream()
                .map(term -> TermAccount.builder()
                        .isAccepted(true)
                        .accountId(command.getAccountId())
                        .termId(term.getId())
                        .build())
                .toList();

        // 약관 동의 저장
        createTermAccountPort.createTermAccounts(termAccounts);

    }

    private void validateAccountTermType(ESecurityRole role, List<ETermType> termTypes) {

        for (ETermType termType : termTypes) {
            switch (termType) {

                case PERSONAL_SERVICE_TERMS: {

                    if(!role.equals(ESecurityRole.USER)) {
                        throw new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE);
                    }
                    break;
                }
                case ENTERPRISE_SERVICE_TERMS: {

                    if(!role.equals(ESecurityRole.OWNER)) {
                        throw new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE);
                    }
                    break;
                }
            }
        }
    }
}
