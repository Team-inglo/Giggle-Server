package com.inglo.giggle.resume.repository.mysql;

import com.inglo.giggle.resume.domain.BookMarkResume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookMarkResumeJpaRepository extends JpaRepository<BookMarkResume, Long> {
}
