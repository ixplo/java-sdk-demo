package com.deepchecks.sdk.types.request;

import com.deepchecks.sdk.types.AnnotationType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateInteractionRequest {
    @JsonProperty("annotation")
    AnnotationType annotation;
    @JsonProperty("annotation_reason")
    String annotationReason;
    @JsonProperty("custom_properties")
    Map<String, Object> customProperties;
}
