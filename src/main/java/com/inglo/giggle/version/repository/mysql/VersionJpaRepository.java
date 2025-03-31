package com.inglo.giggle.version.repository.mysql;

import com.inglo.giggle.version.domain.Version;
import com.inglo.giggle.version.domain.type.EOsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface VersionJpaRepository extends JpaRepository<Version, Long> {

    Optional<Version> findByOsType(EOsType osType);
}
