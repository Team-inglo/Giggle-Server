package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.repository.mysql.OwnerRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EImageType;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.posting.application.usecase.DeleteOwnerJobPostingUseCase;
import com.inglo.giggle.posting.repository.mysql.CompanyImageRepository;
import com.inglo.giggle.posting.repository.mysql.JobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteOwnerJobPostingService implements DeleteOwnerJobPostingUseCase {

    private final OwnerRepository ownerRepository;
    private final JobPostingRepository jobPostingRepository;
    private final CompanyImageRepository companyImageRepository;
    private final S3Util s3Util;

    @Override
    @Transactional
    public void execute(UUID accountId, Long jobPostingId) {

        // 고용주 확인
        Owner owner = ownerRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE));

        // 이미지 S3에서 삭제
        companyImageRepository.findAllByJobPostingId(jobPostingId)
                .forEach(companyImage -> s3Util.deleteFile(
                        companyImage.getImgUrl(),
                        EImageType.COMPANY_IMG,
                        owner.getSerialId())
                );

        // 공고 삭제
        jobPostingRepository.deleteById(jobPostingId);
    }
}
