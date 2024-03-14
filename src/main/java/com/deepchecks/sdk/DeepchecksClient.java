package com.deepchecks.sdk;

import com.deepchecks.request.RequestService;
import com.deepchecks.sdk.types.EnvType;
import com.deepchecks.sdk.types.InteractionRequest;
import com.deepchecks.sdk.types.LogInteractionType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static java.lang.String.format;

public class DeepchecksClient {
    private static final String DEFAULT_HOST = "https://app.llm.deepchecks.com";
    private static final String API_VERSION = "api/v1";
    private static final String DEFAULT_VERSION = "0.0.1";
    private static final EnvType DEFAULT_ENV_TYPE = EnvType.PROD;

    private final String host;
    private final String token;
    private final String appName;
    private final String versionName;
    private final EnvType envType;
    private Boolean autoCollect;
    private Boolean silentMode;

    private RequestService requestService;
    private ObjectMapper objectMapper;
    public DeepchecksClient(String token, String appName, String versionName) {
        this(DEFAULT_HOST, token, appName, versionName, DEFAULT_ENV_TYPE, true, true);
    }

    public DeepchecksClient(String host, String token, String appName, String versionName, EnvType envType, Boolean autoCollect, Boolean silentMode) {
        this.host = host;
        this.token = token;
        this.appName = appName;
        this.versionName = versionName;
        this.envType = envType;
        this.autoCollect = autoCollect;
        this.silentMode = silentMode;
        this.requestService = new RequestService(format("%s/%s", host, API_VERSION), token);
        this.objectMapper = new ObjectMapper();
    }

    public String logBatchInteractions(List<LogInteractionType> interactions) {
        return requestService.post("interactions", toJson(interactions));
    }

    private InteractionRequest toInteractionRequest(List<LogInteractionType> interactions) {
        return InteractionRequest.builder()
                .appName(appName)
                .versionName(versionName)
                .envType(envType)
                .interactions(interactions)
                .build();
    }

    private String toJson(List<LogInteractionType> interactions) {
        return toJson(toInteractionRequest(interactions));
    }
    private String toJson(InteractionRequest interactionRequest) {
        try {
            return objectMapper.writeValueAsString(interactionRequest);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting to json: ", e);
        }
    }

}
