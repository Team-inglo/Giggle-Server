package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.type.EImageType;
import com.inglo.giggle.core.utility.DateTimeUtil;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.posting.application.usecase.UpdateOwnerJobPostingUseCase;
import com.inglo.giggle.posting.domain.CompanyImage;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.PostingWorkDayTime;
import com.inglo.giggle.posting.persistence.repository.CompanyImageRepository;
import com.inglo.giggle.posting.persistence.repository.JobPostingRepository;
import com.inglo.giggle.posting.presentation.dto.request.UpdateOwnerJobPostingRequestDto;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateOwnerJobPostingService implements UpdateOwnerJobPostingUseCase {


    private final AccountRepository accountRepository;
    private final JobPostingRepository jobPostingRepository;
    private final CompanyImageRepository companyImageRepository;

    private final S3Util s3Util;

    @Override
    @Transactional
    public void execute(List<MultipartFile> image, UUID accountId, Long jobPostingId, UpdateOwnerJobPostingRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 검사
        account.checkOwnerValidation();

        // 공고 조회
        JobPosting jobPosting = jobPostingRepository.findByIdOrElseThrow(jobPostingId);

        // 공고 수정 유효성 검사
        jobPosting.validateUpdateJobPosting((Owner) account);

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

        // 공고 수정
        jobPosting.updateSelf(
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
            List<PostingWorkDayTime> workDayTimes = new ArrayList<>();

            requestDto.workDayTimes().forEach(workDayTime -> {
                PostingWorkDayTime postingWorkDayTime = PostingWorkDayTime.builder()
                        .dayOfWeek(workDayTime.dayOfWeek())
                        .workStartTime((workDayTime.workStartTime() == null || workDayTime.workStartTime().isBlank()) ? null : DateTimeUtil.convertStringToLocalTime(workDayTime.workStartTime()))
                        .workEndTime((workDayTime.workEndTime() == null || workDayTime.workEndTime().isBlank()) ? null : DateTimeUtil.convertStringToLocalTime(workDayTime.workEndTime()))
                        .build();
                workDayTimes.add(postingWorkDayTime);
            });
            jobPosting.updatePostWorkDayTimes(workDayTimes);
        }

        if (requestDto.deletedImgIds() != null && !requestDto.deletedImgIds().isEmpty()) {
            companyImageRepository.findAllById(requestDto.deletedImgIds())
                    .forEach(companyImage -> s3Util.deleteFile(
                            companyImage.getImgUrl(),
                            EImageType.COMPANY_IMG,
                            account.getSerialId()
                            )
                    );
            companyImageRepository.deleteAllByIdIn(requestDto.deletedImgIds());
        }

        if (image != null && !image.isEmpty()) {
            image.forEach(img -> {
                String uploadImageFile = s3Util.uploadImageFile(img, account.getSerialId(), EImageType.COMPANY_IMG);
                jobPosting.getCompanyImages().add(
                        CompanyImage.builder()
                                .imgUrl(uploadImageFile)
                                .build()
                );
            });
        }

        jobPostingRepository.save(jobPosting);
    }
}
