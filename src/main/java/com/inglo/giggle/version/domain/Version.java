package com.inglo.giggle.version.domain;

import com.inglo.giggle.version.domain.type.EOsType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Version {

    private Long id;
    private Integer major;
    private Integer minor;
    private Integer patch;
    private EOsType osType;

    @Builder
    public Version(Long id, Integer major, Integer minor, Integer patch, EOsType osType) {
        this.id = id;
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.osType = osType;
    }
}
