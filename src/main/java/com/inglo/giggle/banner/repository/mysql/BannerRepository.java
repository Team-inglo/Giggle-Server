package com.inglo.giggle.banner.repository.mysql;

import com.inglo.giggle.banner.domain.Banner;
import com.inglo.giggle.banner.repository.mysql.querydsl.BannerRepositoryQuery;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long>, BannerRepositoryQuery {

    @Query("SELECT b FROM Banner b WHERE b.role = :role OR b.role = 'GUEST'")
    List<Banner> findByRole(@Param("role") ESecurityRole role);
}
