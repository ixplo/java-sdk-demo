package com.deepchecks.sdk.types;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.deepchecks.util.Const.DATE_TIME_PATTERN_LONG;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogInteractionType {
    String input;
    String output;
    @JsonProperty("full_prompt")
    String fullPrompt;
    AnnotationType annotation;
    @JsonProperty("user_interaction_id")
    String userInteractionId;
    List<Step> steps;
    @JsonProperty("custom_props")
    Map<String, Object> customProps;
    @JsonProperty("information_retrieval")
    String informationRetrieval;
    @JsonProperty("raw_json_data")
    String rawJsonData;
    @JsonProperty("annotation_reason")
    String annotationReason;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN_LONG)
    @JsonProperty("started_at")
    LocalDateTime startedAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN_LONG)
    @JsonProperty("finished_at")
    LocalDateTime finishedAt;
}
