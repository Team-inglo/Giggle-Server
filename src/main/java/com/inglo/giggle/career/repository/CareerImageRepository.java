package com.inglo.giggle.career.repository;

import com.inglo.giggle.career.domain.CareerImage;

import java.util.List;

public interface CareerImageRepository {
    List<CareerImage> findByCareerId(Long careerId);

    void saveAll(List<CareerImage> careerImages);
}
