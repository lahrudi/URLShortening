package com.mbdev.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import static com.google.common.base.Preconditions.checkNotNull;

@Value
@Builder
public class CustomUrlDto {
    @NonNull
    String originalUrl;
    @NonNull
    String shortenerUrl;

    @JsonCreator
    public CustomUrlDto(@JsonProperty("originalUrl") final String originalUrl,
                        @JsonProperty("shortenerUrl") final String shortenerUrl) {
        this.originalUrl = checkNotNull(originalUrl);
        this.shortenerUrl = checkNotNull(shortenerUrl);
    }
}

