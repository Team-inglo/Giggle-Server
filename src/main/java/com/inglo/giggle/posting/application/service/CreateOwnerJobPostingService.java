package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.address.domain.service.AddressService;
import com.inglo.giggle.core.type.EImageType;
import com.inglo.giggle.core.utility.DateTimeUtil;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.posting.application.dto.request.CreateOwnerJobPostingRequestDto;
import com.inglo.giggle.posting.application.dto.response.CreateOwnerJobPostingResponseDto;
import com.inglo.giggle.posting.application.usecase.CreateOwnerJobPostingUseCase;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.service.CompanyImageService;
import com.inglo.giggle.posting.domain.service.JobPostingService;
import com.inglo.giggle.posting.domain.service.PostWorkDayTimeService;
import com.inglo.giggle.posting.repository.JobPostingRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateOwnerJobPostingService implements CreateOwnerJobPostingUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    private final JobPostingService jobPostingService;
    private final PostWorkDayTimeService postWorkDayTimeService;

    private final JobPostingRepository jobPostingRepository;

    private final S3Util s3Util;
    private final CompanyImageService companyImageService;
    private final AddressService addressService;

    @Override
    @Transactional
    public CreateOwnerJobPostingResponseDto execute(UUID accountId, List<MultipartFile> image, CreateOwnerJobPostingRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 검사
        accountService.checkOwnerValidation(account);

        // 고용주 조회
        Owner owner = (Owner) account;

        // 주소 생성
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

        // 공고 생성
        JobPosting jobPosting = jobPostingService.createJobPosting(
                requestDto.title(),
                requestDto.jobCategory(),
                requestDto.hourlyRate(),
                requestDto.recruitmentDeadLine() == null ? null : DateTimeUtil.convertStringToLocalDate(requestDto.recruitmentDeadLine()),
                requestDto.workPeriod(),
                requestDto.recruitmentNumber() == null ? null: requestDto.recruitmentNumber(),
                requestDto.gender(),
                requestDto.ageRestriction() == null ? null : requestDto.ageRestriction(),
                requestDto.educationLevel(),
                requestDto.visa(),
                requestDto.recruiterName(),
                requestDto.recruiterEmail(),
                requestDto.recruiterPhoneNumber(),
                requestDto.description(),
                requestDto.preferredConditions(),
                requestDto.employmentType(),
                owner,
                address
        );

        requestDto.workDayTimes().forEach(workDayTimeDto -> jobPosting.getWorkDayTimes().add(
                postWorkDayTimeService.createPostingWorkDayTime(
                        workDayTimeDto.dayOfWeek(),
                        (workDayTimeDto.workStartTime() == null || workDayTimeDto.workStartTime().isBlank()) ? null : DateTimeUtil.convertStringToLocalTime(workDayTimeDto.workStartTime()),
                        (workDayTimeDto.workEndTime() == null || workDayTimeDto.workEndTime().isBlank() ) ? null : DateTimeUtil.convertStringToLocalTime(workDayTimeDto.workEndTime()),
                        jobPosting
                )
        ));

        if(image != null && !image.isEmpty()) {
            // 이미지 업로드 및 저장
            image.forEach(img -> {
                String uploadImageFile = s3Util.uploadImageFile(img, owner.getSerialId(), EImageType.COMPANY_IMG);
                jobPosting.getCompanyImages().add(
                        companyImageService.createCompanyImage(
                                uploadImageFile,
                                jobPosting
                        )
                );
            });
        }


        JobPosting savedJobPosting = jobPostingRepository.saveAndReturn(jobPosting);

        return CreateOwnerJobPostingResponseDto.builder()
                .id(savedJobPosting.getId())
                .build();
    }


}
