package com.inglo.giggle.version.domain;

import com.inglo.giggle.version.domain.type.EOsType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "versions")
public class Version {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */

    @NotNull(message = "major는 null이 될 수 없습니다.")
    @Column(name = "major")
    private Integer major;

    @NotNull(message = "minor는 null이 될 수 없습니다.")
    @Column(name = "minor")
    private Integer minor;

    @NotNull(message = "patch는 null이 될 수 없습니다.")
    @Column(name = "patch")
    private Integer patch;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "osType은 null이 될 수 없습니다.")
    @Column(name = "os_type")
    private EOsType osType;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public Version(
            Integer major,
            Integer minor,
            Integer patch,
            EOsType osType
    ) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.osType = osType;
    }
}
