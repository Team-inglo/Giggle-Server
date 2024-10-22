package com.inglo.giggle.document.repository.mysql;

import com.inglo.giggle.document.domain.IntegratedApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntegratedApplicationRepository extends JpaRepository<IntegratedApplication, Long> {
}
