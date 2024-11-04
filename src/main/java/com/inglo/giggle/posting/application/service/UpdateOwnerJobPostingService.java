package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.repository.mysql.OwnerRepository;
import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.address.domain.service.AddressService;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EImageType;
import com.inglo.giggle.core.utility.DateTimeUtil;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.posting.application.dto.request.UpdateOwnerJobPostingRequestDto;
import com.inglo.giggle.posting.application.usecase.UpdateOwnerJobPostingUseCase;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.service.CompanyImageService;
import com.inglo.giggle.posting.domain.service.JobPostingService;
import com.inglo.giggle.posting.domain.service.PostWorkDayTimeService;
import com.inglo.giggle.posting.repository.mysql.CompanyImageRepository;
import com.inglo.giggle.posting.repository.mysql.JobPostingRepository;
import com.inglo.giggle.posting.repository.mysql.PostingWorkDayTimeRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateOwnerJobPostingService implements UpdateOwnerJobPostingUseCase {


    private final AccountRepository accountRepository;
    private final AccountService accountService;

    private final OwnerRepository ownerRepository;
    private final JobPostingRepository jobPostingRepository;
    private final PostingWorkDayTimeRepository postingWorkDayTimeRepository;
    private final CompanyImageRepository companyImageRepository;

    private final JobPostingService jobPostingService;
    private final PostWorkDayTimeService postWorkDayTimeService;
    private final CompanyImageService companyImageService;

    private final S3Util s3Util;
    private final AddressService addressService;

    @Override
    @Transactional
    public void execute(List<MultipartFile> image, UUID accountId, Long jobPostingId, UpdateOwnerJobPostingRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 계정 타입 유효성 검사
        accountService.checkOwnerValidation(account);

        // 고용주 조회
        Owner owner = (Owner) account;

        // 공고 조회
        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 공고 수정 유효성 검사
        jobPostingService.validateUpdateJobPosting(jobPosting, owner);

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

        // 공고 수정
        jobPostingService.updateJobPosting(
                jobPosting,
                requestDto.title(),
                requestDto.jobCategory(),
                requestDto.hourlyRate(),
                requestDto.recruitmentDeadLine() == null ? null : LocalDate.parse(requestDto.recruitmentDeadLine()), // 상시 모집일 경우 null 처리
                requestDto.workPeriod(),
                requestDto.recruitmentNumber() == null ? null : requestDto.recruitmentNumber(), // null인 경우 모집인원 무관
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
                address
        );

        if (requestDto.workDayTimes() != null) {
            postingWorkDayTimeRepository.deleteAll(jobPosting.getWorkDayTimes());

            requestDto.workDayTimes().forEach(workDayTime -> postWorkDayTimeService.createPostingWorkDayTime(
                    workDayTime.dayOfWeek(),
                    (workDayTime.workStartTime() == null || workDayTime.workStartTime().isBlank()) ? null : DateTimeUtil.convertStringToLocalTime(workDayTime.workStartTime()),
                    (workDayTime.workEndTime() == null || workDayTime.workEndTime().isBlank()) ? null : DateTimeUtil.convertStringToLocalTime(workDayTime.workEndTime()),
                    jobPosting
            ));
        }

        if (!requestDto.deletedImgIds().isEmpty()) {
            companyImageRepository.findAllById(requestDto.deletedImgIds())
                    .forEach(companyImage -> s3Util.deleteFile(
                            companyImage.getImgUrl(),
                            EImageType.COMPANY_IMG,
                            owner.getSerialId()
                            )
                    );
            companyImageRepository.deleteAllByIdIn(requestDto.deletedImgIds());
        }

        if (image != null && !image.isEmpty()) {
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

        jobPostingRepository.save(jobPosting);
    }
}
