package com.inglo.giggle.banner.repository.mysql;

import com.inglo.giggle.banner.domain.Banner;
import com.inglo.giggle.school.domain.School;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long>{
    List<Banner> findByRole(ESecurityRole role);

    @Query("SELECT b FROM Banner b")
    List<Banner> find();
}
