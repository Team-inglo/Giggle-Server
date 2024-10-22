package com.inglo.giggle.resume.repository.mysql;

import com.inglo.giggle.resume.domain.Education;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education, Long>{
}
