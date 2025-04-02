package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.core.type.EImageType;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.posting.application.usecase.DeleteOwnerJobPostingUseCase;
import com.inglo.giggle.posting.persistence.repository.CompanyImageRepository;
import com.inglo.giggle.posting.persistence.repository.JobPostingRepository;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteOwnerJobPostingService implements DeleteOwnerJobPostingUseCase {

    private final AccountRepository accountRepository;
    private final JobPostingRepository jobPostingRepository;
    private final CompanyImageRepository companyImageRepository;

    private final S3Util s3Util;

    @Override
    @Transactional
    public void execute(UUID accountId, Long jobPostingId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 검사
        account.checkOwnerValidation();

        // 이미지 S3에서 삭제
        companyImageRepository.findAllByJobPostingId(jobPostingId)
                .forEach(companyImage -> s3Util.deleteFile(
                        companyImage.getImgUrl(),
                        EImageType.COMPANY_IMG,
                        account.getSerialId())
                );

        // 공고 삭제
        jobPostingRepository.deleteById(jobPostingId);
    }
}
