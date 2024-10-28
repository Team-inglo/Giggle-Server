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

        // 고용주 조회
        Owner owner = ownerRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE));

        // 공고 조회
        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

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
                requestDto.recruitmentNumber(),
                requestDto.gender(),
                requestDto.ageRestriction(),
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
                    DateTimeUtil.convertStringToLocalTime(workDayTime.workStartTime()),
                    DateTimeUtil.convertStringToLocalTime(workDayTime.workEndTime()),
                    jobPosting
            ));
        }

        if (!requestDto.deletedImgIds().isEmpty()) {
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
