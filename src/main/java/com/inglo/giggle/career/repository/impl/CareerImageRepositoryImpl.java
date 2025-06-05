package com.inglo.giggle.career.repository.impl;

import com.inglo.giggle.career.domain.CareerImage;
import com.inglo.giggle.career.repository.CareerImageRepository;
import com.inglo.giggle.career.repository.mysql.CareerImageJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CareerImageRepositoryImpl implements CareerImageRepository {

    private final CareerImageJpaRepository careerImageJpaRepository;

    @Override
    public List<CareerImage> findByCareerId(Long careerId) {
        return careerImageJpaRepository.findByCareerId(careerId);
    }

    @Override
    public void saveAll(List<CareerImage> careerImages) {
        careerImageJpaRepository.saveAll(careerImages);
    }

    @Override
    public List<CareerImage> findAllById(List<Long> careerImageIds) {
        return careerImageJpaRepository.findAllById(careerImageIds);
    }

    @Override
    public void delete(CareerImage careerImage) {
        careerImageJpaRepository.delete(careerImage);
    }
}
