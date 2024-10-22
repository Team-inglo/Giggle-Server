package com.inglo.giggle.resume.repository.mysql;

import com.inglo.giggle.resume.domain.AdditionalLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AdditionalLanguageRepository extends JpaRepository<AdditionalLanguage, Long> {
}
