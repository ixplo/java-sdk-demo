package com.deepchecks.sdk.types.request;

import com.deepchecks.sdk.types.EnvType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataRequest {
    @JsonProperty("application_version_id")
    String applicationVersionId;
    @JsonProperty("environment")
    EnvType environment;
    @JsonProperty("start_time_epoch")
    Long startTimeEpoch;
    @JsonProperty("end_time_epoch")
    Long endTimeEpoch;
}
