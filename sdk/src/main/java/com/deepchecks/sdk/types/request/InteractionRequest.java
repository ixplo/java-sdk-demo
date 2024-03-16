package com.deepchecks.sdk.types.request;

import com.deepchecks.sdk.types.EnvType;
import com.deepchecks.sdk.types.LogInteractionType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InteractionRequest {
    @JsonProperty("app_name")
    String appName;
    @JsonProperty("version_name")
    String versionName;
    @JsonProperty("env_type")
    EnvType envType;
    List<LogInteractionType> interactions;
}
