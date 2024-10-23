package com.inglo.giggle.school.repository.mysql;

import com.inglo.giggle.school.domain.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SchoolRepository extends JpaRepository<School, Long>{
}
