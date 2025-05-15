package com.inglo.giggle.career.repository.mysql;

import com.inglo.giggle.career.domain.Career;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CareerJpaRepository extends JpaRepository<Career, Long> {

}
