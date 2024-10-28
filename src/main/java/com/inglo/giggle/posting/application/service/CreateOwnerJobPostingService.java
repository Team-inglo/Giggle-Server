package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.repository.mysql.OwnerRepository;
import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EImageType;
import com.inglo.giggle.core.utility.DateTimeUtil;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.posting.application.dto.request.CreateOwnerJobPostingRequestDto;
import com.inglo.giggle.posting.application.dto.response.CreateOwnerJobPostingResponseDto;
import com.inglo.giggle.posting.application.usecase.CreateOwnerJobPostingUseCase;
import com.inglo.giggle.posting.domain.CompanyImage;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.service.CompanyImageService;
import com.inglo.giggle.posting.domain.service.JobPostingService;
import com.inglo.giggle.posting.domain.service.PostWorkDayTimeService;
import com.inglo.giggle.posting.repository.mysql.CompanyImageRepository;
import com.inglo.giggle.posting.repository.mysql.JobPostingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateOwnerJobPostingService implements CreateOwnerJobPostingUseCase {

    private final JobPostingService jobPostingService;
    private final PostWorkDayTimeService postWorkDayTimeService;

    private final OwnerRepository ownerRepository;
    private final JobPostingRepository jobPostingRepository;

    private final S3Util s3Util;
    private final CompanyImageService companyImageService;
    private final CompanyImageRepository companyImageRepository;

    @Override
    @Transactional
    public CreateOwnerJobPostingResponseDto execute(UUID accountId, List<MultipartFile> image, CreateOwnerJobPostingRequestDto requestDto) {

        log.info("image size : {}", image.size());
        log.info("image : {}", image);

        // 고용주 조회
        Owner owner = ownerRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 주소 생성
        Address address = Address.builder()
                .addressName(requestDto.address().addressName())
                .region1DepthName(requestDto.address().region1DepthName())
                .region2DepthName(requestDto.address().region2DepthName())
                .region3DepthName(requestDto.address().region3DepthName())
                .addressDetail(requestDto.address().addressDetail())
                .latitude(requestDto.address().latitude())
                .longitude(requestDto.address().longitude())
                .build();


        // 공고 생성
        JobPosting jobPosting = jobPostingService.createJobPosting(
                requestDto.title(),
                requestDto.jobCategory(),
                requestDto.hourlyRate(),
                requestDto.recruitmentDeadLine() == null ? null : DateTimeUtil.convertStringToLocalDate(requestDto.recruitmentDeadLine()),
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
                owner,
                address
        );

        requestDto.workDayTimes().forEach(workDayTimeDto -> jobPosting.getWorkDayTimes().add(
                postWorkDayTimeService.createPostingWorkDayTime(
                        workDayTimeDto.dayOfWeek(),
                        DateTimeUtil.convertStringToLocalTime(workDayTimeDto.workStartTime()),
                        DateTimeUtil.convertStringToLocalTime(workDayTimeDto.workEndTime()),
                        jobPosting
                )
        ));

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

        JobPosting savedJobPosting = jobPostingRepository.save(jobPosting);

        return CreateOwnerJobPostingResponseDto.builder()
                .id(savedJobPosting.getId())
                .build();
    }


}
