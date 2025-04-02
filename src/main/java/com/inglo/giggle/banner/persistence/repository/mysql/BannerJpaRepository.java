package com.inglo.giggle.banner.persistence.repository.mysql;

import com.inglo.giggle.banner.persistence.entity.BannerEntity;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BannerJpaRepository extends JpaRepository<BannerEntity, Long> {

    @Query("SELECT b FROM BannerEntity b WHERE b.role = :role OR b.role = 'GUEST'")
    List<BannerEntity> findByRole(@Param("role") ESecurityRole role);
}
