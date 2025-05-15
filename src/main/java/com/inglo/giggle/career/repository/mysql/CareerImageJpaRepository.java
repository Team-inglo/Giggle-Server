package com.inglo.giggle.career.repository.mysql;

import com.inglo.giggle.career.domain.CareerImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CareerImageJpaRepository extends JpaRepository<CareerImage, Long> {
    List<CareerImage> findByCareerId(Long careerId);
}
