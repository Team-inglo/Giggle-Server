package com.inglo.giggle.document.repository.mysql;

import com.inglo.giggle.document.domain.IntegratedApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface IntegratedApplicationRepository extends JpaRepository<IntegratedApplication, Long> {
}
