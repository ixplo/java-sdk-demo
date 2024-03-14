package com.deepchecks.sdk.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataRequest {
    @JsonProperty("application_version_id")
    String applicationVersionId;
    @JsonProperty("environment")
    EnvType environment;
    @JsonProperty("start_time_epoch")
    LocalDateTime startTimeEpoch;
    @JsonProperty("end_time_epoch")
    LocalDateTime endTimeEpoch;
}
