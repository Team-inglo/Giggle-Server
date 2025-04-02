package com.inglo.giggle.term.domain.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import com.inglo.giggle.term.domain.Term;
import com.inglo.giggle.term.domain.TermAccount;
import com.inglo.giggle.term.persistence.entity.TermAccountEntity;
import com.inglo.giggle.term.persistence.entity.TermEntity;
import com.inglo.giggle.term.domain.type.ETermType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TermAccountService {

    public void validateAccountTermType(Account account, List<ETermType> termTypes) {

        for (ETermType termType : termTypes) {
            switch (termType) {

                case PERSONAL_SERVICE_TERMS: {

                    if(!account.getRole().equals(ESecurityRole.USER)) {
                        throw new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE);
                    }
                    break;
                }
                case ENTERPRISE_SERVICE_TERMS: {

                    if(!account.getRole().equals(ESecurityRole.OWNER)) {
                        throw new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE);
                    }
                    break;
                }
            }
        }
    }

    public List<TermAccount> createTermAccount(Account account, List<Term> terms) {
        return terms.stream()
                .map(term -> TermAccount.builder()
                        .isAccepted(true)
                        .accountId(account.getId())
                        .termId(term.getId())
                        .build())
                .toList();
    }
}
