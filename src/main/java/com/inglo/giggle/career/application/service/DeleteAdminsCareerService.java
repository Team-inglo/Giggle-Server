package com.inglo.giggle.career.application.service;

import com.inglo.giggle.career.application.usecase.DeleteAdminsCareerUseCase;
import com.inglo.giggle.career.domain.Career;
import com.inglo.giggle.career.domain.CareerImage;
import com.inglo.giggle.career.repository.CareerImageRepository;
import com.inglo.giggle.career.repository.CareerRepository;
import com.inglo.giggle.core.type.EImageType;
import com.inglo.giggle.core.utility.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeleteAdminsCareerService implements DeleteAdminsCareerUseCase {

    private final CareerRepository careerRepository;
    private final CareerImageRepository careerImageRepository;

    private final S3Util s3Util;

    @Override
    @Transactional
    public void execute(Long careerId) {
        List<CareerImage> careerImages = careerImageRepository.findAllByCareerId(careerId);

        careerImages.forEach( careerImage -> {
            s3Util.deleteFile(
                    careerImage.getImgUrl(),
                    EImageType.CAREER_IMG,
                    careerImage.getSerialId().toString()
            );
            careerImageRepository.delete(careerImage);
        });

        Career career = careerRepository.findByIdOrElseThrow(careerId);
        careerRepository.delete(career);
    }
}
