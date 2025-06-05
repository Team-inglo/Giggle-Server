package com.inglo.giggle.career.application.service;

import com.inglo.giggle.career.application.dto.request.UpdateAdminsCareerRequestDto;
import com.inglo.giggle.career.application.usecase.UpdateAdminsCareerUseCase;
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
public class UpdateAdminsCareerService implements UpdateAdminsCareerUseCase {

    private final CareerRepository careerRepository;
    private final CareerImageRepository careerImageRepository;

    private final S3Util s3Util;

    @Override
    @Transactional
    public void execute(Long careerId, List<MultipartFile> images, UpdateAdminsCareerRequestDto requestDto) {

        Career career = careerRepository.findByIdOrElseThrow(careerId);
        career.updateSelf(
                requestDto.title(),
                requestDto.hostName(),
                requestDto.organizerName(),
                requestDto.address(),
                DateTimeUtil.convertStringToLocalDate(requestDto.recruitmentStartDate()),
                DateTimeUtil.convertStringToLocalDate(requestDto.recruitmentEndDate()),
                requestDto.reward(),
                requestDto.visa(),
                requestDto.recruitmentNumber(),
                requestDto.education(),
                requestDto.preferredConditions(),
                requestDto.careerCategory(),
                requestDto.details(),
                requestDto.applicationUrl()
        );

        if(requestDto.deleteImgIds() != null && !requestDto.deleteImgIds().isEmpty()) {
            careerImageRepository.findAllById(requestDto.deleteImgIds())
                    .forEach(careerImage -> {
                        s3Util.deleteFile(
                                careerImage.getImgUrl(),
                                EImageType.CAREER_IMG,
                                careerImage.getSerialId().toString()
                        );
                        careerImageRepository.delete(careerImage);
                    });
        }

        if(images != null && !images.isEmpty()) {
            UUID serialId = UUID.randomUUID();

            List<String> imageUrls = images.stream().map(image -> s3Util.uploadImageFile(
                    image,
                    serialId.toString(),
                    EImageType.CAREER_IMG
            )).toList();

            List<CareerImage> careerImages = imageUrls.stream()
                    .map(url -> CareerImage.builder()
                            .imgUrl(url)
                            .serialId(serialId)
                            .career(career)
                            .build())
                    .toList();

            careerImageRepository.saveAll(careerImages);
        }

        careerRepository.save(career);
    }
}
