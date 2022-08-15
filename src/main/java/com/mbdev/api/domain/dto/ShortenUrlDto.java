package com.mbdev.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import static com.google.common.base.Preconditions.checkNotNull;

@Value
@Builder
public class ShortenUrlDto {
    @NonNull
    String originalUrl;

    @JsonCreator
    public ShortenUrlDto(@JsonProperty("originalUrl") final String originalUrl) {
        this.originalUrl = checkNotNull(originalUrl);
    }
}

