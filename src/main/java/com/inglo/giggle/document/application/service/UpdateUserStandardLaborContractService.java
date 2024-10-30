package com.inglo.giggle.document.application.service;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.address.domain.service.AddressService;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.dto.request.UpdateUserStandardLaborContractRequestDto;
import com.inglo.giggle.document.application.usecase.UpdateUserStandardLaborContractUseCase;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.domain.service.StandardLaborContractService;
import com.inglo.giggle.document.domain.type.EEmployeeStatus;
import com.inglo.giggle.document.repository.mysql.StandardLaborContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateUserStandardLaborContractService implements UpdateUserStandardLaborContractUseCase {
    private final StandardLaborContractRepository standardLaborContractRepository;
    private final StandardLaborContractService standardLaborContractService;
    private final AddressService addressService;

    @Override
    @Transactional
    public void execute(Long documentId, UpdateUserStandardLaborContractRequestDto requestDto) {
        StandardLaborContract standardLaborContract = standardLaborContractRepository.findById(documentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        if (!standardLaborContract.getEmployeeStatus().equals(EEmployeeStatus.TEMPORARY_SAVE))
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

        StandardLaborContract updatedStandardLaborContract = standardLaborContractService.updateUserStandardLaborContract(
                standardLaborContract,
                requestDto.firstName(),
                requestDto.lastName(),
                address,
                requestDto.phoneNumber(),
                requestDto.signatureBase64()
        );

        standardLaborContractRepository.save(updatedStandardLaborContract);
    }
}
