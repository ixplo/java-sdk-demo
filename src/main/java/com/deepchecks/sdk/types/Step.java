package com.deepchecks.sdk.types;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

import static com.deepchecks.util.Const.DATE_TIME_PATTERN_LONG;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Step {
    String name;
    StepType type;
    Map<String, Object> attributes;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN_LONG)
    @JsonProperty("started_at")
    LocalDateTime startedAt;
    AnnotationType annotation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN_LONG)
    @JsonProperty("finished_at")
    LocalDateTime finishedAt;
    String input;
    String output;
    String error;
}
