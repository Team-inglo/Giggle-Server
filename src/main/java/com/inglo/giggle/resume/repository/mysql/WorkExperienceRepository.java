package com.inglo.giggle.resume.repository.mysql;

import com.inglo.giggle.resume.domain.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long>{
}
