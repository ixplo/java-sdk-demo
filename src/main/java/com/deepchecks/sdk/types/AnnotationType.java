package com.deepchecks.sdk.types;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AnnotationType {
    GOOD,
    BAD,
    UNKNOWN;

    @JsonValue
    String getName() {
        return name().toLowerCase();
    }
}
