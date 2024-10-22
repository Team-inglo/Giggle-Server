package com.inglo.giggle.resume.repository.mysql;

import com.inglo.giggle.resume.domain.AdditionalLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdditionalLanguageRepository extends JpaRepository<AdditionalLanguage, Long> {
}
