package com.inglo.giggle.document.application.service;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.address.domain.service.AddressService;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.dto.request.UpdateOwnerPartTimeEmploymentPermitRequestDto;
import com.inglo.giggle.document.application.dto.request.UpdateUserPartTimeEmploymentPermitRequestDto;
import com.inglo.giggle.document.application.usecase.UpdateOwnerPartTimeEmploymentPermitUseCase;
import com.inglo.giggle.document.application.usecase.UpdateUserPartTimeEmploymentPermitUseCase;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.service.PartTimeEmploymentPermitService;
import com.inglo.giggle.document.domain.type.EEmployerStatus;
import com.inglo.giggle.document.repository.mysql.PartTimeEmploymentPermitRepository;
import com.inglo.giggle.posting.domain.type.EWorkPeriod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateOwnerPartTimeEmploymentPermitService implements UpdateOwnerPartTimeEmploymentPermitUseCase {
    private final PartTimeEmploymentPermitRepository partTimeEmploymentPermitRepository;
    private final PartTimeEmploymentPermitService partTimeEmploymentPermitService;
    private final AddressService addressService;

    @Override
    @Transactional
    public void execute(Long documentId, UpdateOwnerPartTimeEmploymentPermitRequestDto requestDto) {
        PartTimeEmploymentPermit partTimeEmploymentPermit = partTimeEmploymentPermitRepository.findById(documentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));
        if (partTimeEmploymentPermit.getEmployerStatus() != null)
            if (!partTimeEmploymentPermit.getEmployerStatus().equals(EEmployerStatus.TEMPORARY_SAVE) &&
                    !partTimeEmploymentPermit.getEmployerStatus().equals(EEmployerStatus.REWRITING))
                throw new CommonException(ErrorCode.ACCESS_DENIED);

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
