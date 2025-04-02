package com.inglo.giggle.version.persistence.repository.mysql;

import com.inglo.giggle.version.persistence.entity.VersionEntity;
import com.inglo.giggle.version.domain.type.EOsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VersionJpaRepository extends JpaRepository<VersionEntity, Long> {

    Optional<VersionEntity> findByOsType(EOsType osType);
}
