package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.usecase.CreateUserPartTimeEmploymentPermitUseCase;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.type.EEmployeeStatus;
import com.inglo.giggle.document.persistence.repository.PartTimeEmploymentPermitRepository;
import com.inglo.giggle.document.presentation.dto.request.CreateUserPartTimeEmploymentPermitRequestDto;
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
public class CreateUserPartTimeEmploymentPermitService implements CreateUserPartTimeEmploymentPermitUseCase {

    private final AccountRepository accountRepository;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final PartTimeEmploymentPermitRepository partTimeEmploymentPermitRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, Long userOwnerJobPostingId, CreateUserPartTimeEmploymentPermitRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        account.checkUserValidation();

        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithOwnerAndUserJobPostingByIdOrElseThrow(userOwnerJobPostingId);

        // 해당 UserOwnerJobPosting 과 연결된 PartTimeEmploymentPermit 이 이미 존재하는지 확인
        if (partTimeEmploymentPermitRepository.findByUserOwnerJobPostingIdOrElseNull(userOwnerJobPostingId) != null) {
            throw new CommonException(ErrorCode.ALREADY_EXIST_RESOURCE);
        }

        // UserOwnerJobPosting 유저 유효성 체크
        userOwnerJobPosting.checkUserUserOwnerJobPostingValidation(accountId);

        // PartTimeEmploymentPermit 생성
        PartTimeEmploymentPermit partTimeEmploymentPermit = PartTimeEmploymentPermit.builder()
                .rejects(null)
                .employeeFirstName(requestDto.firstName())
                .employeeLastName(requestDto.lastName())
                .major(requestDto.major())
                .termOfCompletion(requestDto.termOfCompletion())
                .employeePhoneNumber(requestDto.phoneNumber())
                .employeeEmail(requestDto.email())
                .employeeStatus(EEmployeeStatus.TEMPORARY_SAVE)
                .build();

        partTimeEmploymentPermitRepository.save(partTimeEmploymentPermit);
    }

}
