package com.inglo.giggle.career.application.service;

import com.inglo.giggle.career.application.dto.response.ReadAdminsCareersDetailsResponseDto;
import com.inglo.giggle.career.application.usecase.ReadAdminsCareersDetailsUseCase;
import com.inglo.giggle.career.domain.Career;
import com.inglo.giggle.career.domain.CareerImage;
import com.inglo.giggle.career.repository.CareerImageRepository;
import com.inglo.giggle.career.repository.CareerRepository;
import com.inglo.giggle.core.utility.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReadAdminsCareersDetailsService implements ReadAdminsCareersDetailsUseCase {

    private final CareerRepository careerRepository;
    private final CareerImageRepository careerImageRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadAdminsCareersDetailsResponseDto execute(Long careerId) {

        List<CareerImage> careerImages = careerImageRepository.findByCareerId(careerId);
        Career career = careerRepository.findByIdOrElseThrow(careerId);

        List<ReadAdminsCareersDetailsResponseDto.imgs> imgs = careerImages.stream().map(careerImage ->
                ReadAdminsCareersDetailsResponseDto.imgs.builder()
                        .id(careerImage.getId())
                        .url(careerImage.getImgUrl())
                        .build()
        ).toList();

        return ReadAdminsCareersDetailsResponseDto.builder()
                .imgs(imgs)
                .title(career.getTitle())
                .careerCategory(career.getCategory())
                .hostName(career.getHost())
                .organizerName(career.getOrganizer())
                .recruitmentStartDate(DateTimeUtil.convertLocalDateToString(career.getStartDate()))
                .recruitmentEndDate(DateTimeUtil.convertLocalDateToString(career.getEndDate()))
                .reward(career.getReward())
                .visa(new HashSet<>(career.getVisa()))
                .recruitmentNumber(career.getRecruitmentNumber())
                .education(career.getEducationLevel())
                .preferredConditions(career.getPreferredConditions())
                .details(career.getDetails())
                .address(career.getAddress())
                .applicationUrl(career.getApplicationUrl())
                .build();
    }
}
