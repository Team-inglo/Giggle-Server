package com.inglo.giggle.resume.repository.mysql;

import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkExperienceJpaRepository extends JpaRepository<WorkExperience, Long>{

    List<WorkExperience> findAllByResume(Resume resume);
}
