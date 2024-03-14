package com.deepchecks.sdk.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogInteractionType {
    String input;
    String output;
    String fullPrompt;
    AnnotationType annotation;
    String userInteractionId;
    List<Step> steps;
    Map<String, Object> customProps;
    String informationRetrieval;
    String rawJsonData;
    String annotationReason;
    LocalDateTime startedAt;
    LocalDateTime finishedAt;
}
