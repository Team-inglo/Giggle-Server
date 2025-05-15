package com.inglo.giggle.career.application.service;

import com.inglo.giggle.career.application.dto.request.CreateAdminsCareerRequestDto;
import com.inglo.giggle.career.application.usecase.CreateAdminsCareerUseCase;
import com.inglo.giggle.career.domain.Career;
import com.inglo.giggle.career.domain.CareerImage;
import com.inglo.giggle.career.repository.CareerImageRepository;
import com.inglo.giggle.career.repository.CareerRepository;
import com.inglo.giggle.core.type.EImageType;
import com.inglo.giggle.core.utility.DateTimeUtil;
import com.inglo.giggle.core.utility.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateAdminsCareerService implements CreateAdminsCareerUseCase {

    private final CareerRepository careerRepository;
    private final CareerImageRepository careerImageRepository;

    private final S3Util s3Util;

    @Override
    @Transactional
    public void execute(List<MultipartFile> images, CreateAdminsCareerRequestDto requestDto) {

        Career career = Career.builder()
                .title(requestDto.title())
                .host(requestDto.hostName())
                .category(requestDto.careerCategory())
                .organizer(requestDto.organizerName())
                .address(requestDto.address())
                .startDate(DateTimeUtil.convertStringToLocalDate(requestDto.recruitmentStartDate()))
                .endDate(DateTimeUtil.convertStringToLocalDate(requestDto.recruitmentEndDate()))
                .reward(requestDto.reward())
                .visa(requestDto.visa())
                .recruitmentNumber(requestDto.recruitmentNumber())
                .educationLevel(requestDto.education())
                .preferredConditions(requestDto.preferredConditions())
                .details(requestDto.details())
                .applicationUrl(requestDto.applicationUrl())
                .build();

        careerRepository.save(career);


        List<String> imageUrls;
        if (images != null && !images.isEmpty()) {
            imageUrls = images.stream().map(image -> s3Util.uploadImageFile(
                    image,
                    UUID.randomUUID().toString(),
                    EImageType.CAREER_IMG
            )).toList();

            List<CareerImage> careerImages = imageUrls.stream()
                    .map(url -> CareerImage.builder()
                            .career(career)
                            .imgUrl(url)
                            .build())
                    .toList();
            careerImageRepository.saveAll(careerImages);
        }
    }
}
