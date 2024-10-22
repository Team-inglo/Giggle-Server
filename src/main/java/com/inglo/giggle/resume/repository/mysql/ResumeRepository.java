package com.inglo.giggle.resume.repository.mysql;

import com.inglo.giggle.resume.domain.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ResumeRepository extends JpaRepository<Resume, UUID>{
}
