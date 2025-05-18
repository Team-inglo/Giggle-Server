package com.inglo.giggle.career.application.service;

import com.inglo.giggle.career.application.dto.response.ReadGuestsCareersDetailsResponseDto;
import com.inglo.giggle.career.application.dto.response.ReadUsersCareersDetailsResponseDto;
import com.inglo.giggle.career.application.usecase.ReadGuestsCareersDetailsUseCase;
import com.inglo.giggle.career.domain.Career;
import com.inglo.giggle.career.domain.CareerImage;
import com.inglo.giggle.career.repository.BookMarkCareerRepository;
import com.inglo.giggle.career.repository.CareerImageRepository;
import com.inglo.giggle.career.repository.CareerRepository;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.core.utility.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReadGuestsCareersDetailsService implements ReadGuestsCareersDetailsUseCase {

    private final CareerRepository careerRepository;
    private final CareerImageRepository careerImageRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadGuestsCareersDetailsResponseDto execute(Long careerId) {

        List<CareerImage> careerImages = careerImageRepository.findByCareerId(careerId);
        List<String> imageUrls = careerImages.stream().map(
                CareerImage::getImgUrl
        ).toList();

        Career career = careerRepository.findByIdOrElseThrow(careerId);
//        Set<EVisa> visa = career.getVisa();

        return ReadGuestsCareersDetailsResponseDto.builder()
                .imgUrls(imageUrls)
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
