package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.core.domain.Address;
import com.inglo.giggle.core.type.EImageType;
import com.inglo.giggle.core.utility.DateTimeUtil;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.posting.application.usecase.CreateOwnerJobPostingUseCase;
import com.inglo.giggle.posting.domain.CompanyImage;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.PostingWorkDayTime;
import com.inglo.giggle.posting.persistence.repository.JobPostingRepository;
import com.inglo.giggle.posting.presentation.dto.request.CreateOwnerJobPostingRequestDto;
import com.inglo.giggle.posting.presentation.dto.response.CreateOwnerJobPostingResponseDto;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateOwnerJobPostingService implements CreateOwnerJobPostingUseCase {

    private final LoadAccountPort loadAccountPort;
    private final JobPostingRepository jobPostingRepository;

    private final S3Util s3Util;

    @Override
    @Transactional
    public CreateOwnerJobPostingResponseDto execute(UUID accountId, List<MultipartFile> image, CreateOwnerJobPostingRequestDto requestDto) {

        // Account 조회
        Account account = loadAccountPort.loadAccountWithRefreshTokenOrElseThrow(accountId);

        // 계정 타입 유효성 검사
        account.checkOwnerValidation();

        // Address 생성
        Address address = Address.builder()
                .addressName(requestDto.address().addressName())
                .addressDetail(requestDto.address().addressDetail())
                .region1DepthName(requestDto.address().region1DepthName())
                .region2DepthName(requestDto.address().region2DepthName())
                .region3DepthName(requestDto.address().region3DepthName())
                .region4DepthName(requestDto.address().region4DepthName())
                .latitude(requestDto.address().latitude())
                .longitude(requestDto.address().longitude())
                .build();

        JobPosting jobPosting = JobPosting.builder()
                .title(requestDto.title())
                .jobCategory(requestDto.jobCategory())
                .hourlyRate(requestDto.hourlyRate())
                .recruitmentDeadLine(requestDto.recruitmentDeadLine() == null ? null : DateTimeUtil.convertStringToLocalDate(requestDto.recruitmentDeadLine()))
                .workPeriod(requestDto.workPeriod())
                .recruitmentNumber(requestDto.recruitmentNumber() == null ? null : requestDto.recruitmentNumber())
                .gender(requestDto.gender())
                .ageRestriction(requestDto.ageRestriction() == null ? null : requestDto.ageRestriction())
                .educationLevel(requestDto.educationLevel())
                .visa(requestDto.visa())
                .recruiterName(requestDto.recruiterName())
                .recruiterEmail(requestDto.recruiterEmail())
                .recruiterPhoneNumber(requestDto.recruiterPhoneNumber())
                .description(requestDto.description())
                .preferredConditions(requestDto.preferredConditions())
                .employmentType(requestDto.employmentType())
                .ownerId(account.getId())
                .address(address)
                .build();

        requestDto.workDayTimes().forEach(workDayTimeDto -> jobPosting.getWorkDayTimes().add(
                        PostingWorkDayTime.builder()
                                .dayOfWeek(workDayTimeDto.dayOfWeek())
                                .workStartTime(workDayTimeDto.workStartTime() == null || workDayTimeDto.workStartTime().isBlank() ? null : DateTimeUtil.convertStringToLocalTime(workDayTimeDto.workStartTime()))
                                .workEndTime(workDayTimeDto.workEndTime() == null || workDayTimeDto.workEndTime().isBlank() ? null : DateTimeUtil.convertStringToLocalTime(workDayTimeDto.workEndTime()))
                                .jobPostingId(jobPosting.getId())
                                .build()
                )
        );

        if (image != null && !image.isEmpty()) {
            // 이미지 업로드 및 저장
            image.forEach(img -> {
                String uploadImageFile = s3Util.uploadImageFile(img, account.getSerialId(), EImageType.COMPANY_IMG);
                jobPosting.getCompanyImages().add(
                        CompanyImage.builder()
                                .imgUrl(uploadImageFile)
                                .jobPostingId(jobPosting.getId())
                                .build()
                );
            });
        }

        JobPosting savedJobPosting = jobPostingRepository.save(jobPosting);

        return CreateOwnerJobPostingResponseDto.builder()
                .id(savedJobPosting.getId())
                .build();
    }


}
