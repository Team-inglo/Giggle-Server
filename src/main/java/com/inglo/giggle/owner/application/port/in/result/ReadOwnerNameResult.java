package com.inglo.giggle.owner.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import lombok.Getter;

@Getter
public class ReadOwnerNameResult extends SelfValidating<ReadOwnerNameResult> {

    @JsonProperty("name")
    private final String name;

    public ReadOwnerNameResult(String name) {
        this.name = name;
        this.validateSelf();
    }
}
