package com.inglo.giggle.document.application.service;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.address.domain.service.AddressService;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.dto.request.CreateUserStandardLaborContractRequestDto;
import com.inglo.giggle.document.application.usecase.CreateUserStandardLaborContractUseCase;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.domain.service.StandardLaborContractService;
import com.inglo.giggle.document.repository.mysql.StandardLaborContractRepository;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.repository.mysql.UserOwnerJobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateUserStandardLaborContractService implements CreateUserStandardLaborContractUseCase {
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final StandardLaborContractRepository standardLaborContractRepository;
    private final StandardLaborContractService standardLaborContractService;
    private final AddressService addressService;

    @Override
    @Transactional
    public void execute(Long id, CreateUserStandardLaborContractRequestDto requestDto) {
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        Address address = addressService.createAddress(requestDto.address());

        StandardLaborContract standardLaborContract = standardLaborContractService.createStandardLaborContract(
                userOwnerJobPosting,
                requestDto.firstName(),
                requestDto.lastName(),
                address,
                requestDto.phoneNumber(),
                requestDto.signatureBase64()
        );

        standardLaborContractRepository.save(standardLaborContract);
    }
}
