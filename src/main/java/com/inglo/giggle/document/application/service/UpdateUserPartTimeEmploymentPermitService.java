package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.dto.request.UpdateUserPartTimeEmploymentPermitRequestDto;
import com.inglo.giggle.document.application.usecase.UpdateUserPartTimeEmploymentPermitUseCase;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.service.PartTimeEmploymentPermitService;
import com.inglo.giggle.document.domain.type.EEmployeeStatus;
import com.inglo.giggle.document.repository.mysql.PartTimeEmploymentPermitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserPartTimeEmploymentPermitService implements UpdateUserPartTimeEmploymentPermitUseCase {
    private final PartTimeEmploymentPermitRepository partTimeEmploymentPermitRepository;
    private final PartTimeEmploymentPermitService partTimeEmploymentPermitService;

    @Override
    @Transactional
    public void execute(UUID accountId, Long documentId, UpdateUserPartTimeEmploymentPermitRequestDto requestDto) {
        PartTimeEmploymentPermit partTimeEmploymentPermit = partTimeEmploymentPermitRepository.findById(documentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        if (!partTimeEmploymentPermit.getEmployeeStatus().equals(EEmployeeStatus.TEMPORARY_SAVE))
            throw new CommonException(ErrorCode.ACCESS_DENIED);

        PartTimeEmploymentPermit updatedPartTimeEmploymentPermit = partTimeEmploymentPermitService.updateUserPartTimeEmploymentPermit(
                partTimeEmploymentPermit,
                requestDto.firstName(),
                requestDto.lastName(),
                requestDto.major(),
                requestDto.termOfCompletion(),
                requestDto.phoneNumber(),
                requestDto.email()
        );

        partTimeEmploymentPermitRepository.save(updatedPartTimeEmploymentPermit);
    }
}
