package com.inglo.giggle.posting.repository.mysql;

import com.inglo.giggle.posting.domain.CompanyImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CompanyImageRepository extends JpaRepository<CompanyImage, Long>{
}
