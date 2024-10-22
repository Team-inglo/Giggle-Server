package com.inglo.giggle.resume.repository.mysql;

import com.inglo.giggle.resume.domain.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface EducationRepository extends JpaRepository<Education, Long>{
}
