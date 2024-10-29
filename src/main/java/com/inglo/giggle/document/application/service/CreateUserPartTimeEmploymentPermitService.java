package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.dto.request.CreateUserPartTimeEmploymentPermitRequestDto;
import com.inglo.giggle.document.application.usecase.CreateUserPartTimeEmploymentPermitUseCase;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.service.PartTimeEmploymentPermitService;
import com.inglo.giggle.document.repository.mysql.PartTimeEmploymentPermitRepository;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.repository.mysql.UserOwnerJobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserPartTimeEmploymentPermitService implements CreateUserPartTimeEmploymentPermitUseCase {

    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final PartTimeEmploymentPermitRepository partTimeEmploymentPermitRepository;
    private final PartTimeEmploymentPermitService partTimeEmploymentPermitService;

    @Override
    public void execute(Long userOwnerJobPostingId, CreateUserPartTimeEmploymentPermitRequestDto requestDto) {
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findById(userOwnerJobPostingId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        PartTimeEmploymentPermit partTimeEmploymentPermit = partTimeEmploymentPermitService.createPartTimeEmploymentPermit(
                userOwnerJobPosting,
                requestDto.firstName(),
                requestDto.lastName(),
                requestDto.major(),
                requestDto.termOfCompletion(),
                requestDto.phoneNumber(),
                requestDto.email()
        );

        partTimeEmploymentPermitRepository.save(partTimeEmploymentPermit);
    }

}
