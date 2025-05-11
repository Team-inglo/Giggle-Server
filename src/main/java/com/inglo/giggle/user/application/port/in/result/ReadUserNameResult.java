package com.inglo.giggle.user.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import lombok.Getter;

@Getter
public class ReadUserNameResult extends SelfValidating<ReadUserNameResult> {

    @JsonProperty("name")
    private final String name;

    public ReadUserNameResult(String name) {
        this.name = name;
        this.validateSelf();
    }
}
