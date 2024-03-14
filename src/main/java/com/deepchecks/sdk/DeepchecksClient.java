package com.deepchecks.sdk;

import com.deepchecks.json.JsonService;
import com.deepchecks.request.RequestService;
import com.deepchecks.sdk.types.AnnotationType;
import com.deepchecks.sdk.types.request.DataRequest;
import com.deepchecks.sdk.types.EnvType;
import com.deepchecks.sdk.types.request.InteractionRequest;
import com.deepchecks.sdk.types.LogInteractionType;
import com.deepchecks.sdk.types.request.UpdateInteractionRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Arrays.asList;

@Builder(toBuilder = true, builderClassName = "DeepchecksClientInternalBuilder", builderMethodName = "internalBuilder")
@AllArgsConstructor
public class DeepchecksClient {
    private static final String URL_PATTERN = "%s/%s";
    private static final String DEFAULT_HOST = "https://app.llm.deepchecks.com";
    private static final String API_VERSION = "api/v1";
    private static final String DEFAULT_VERSION = "0.0.1";
    private static final EnvType DEFAULT_ENV_TYPE = EnvType.PROD;

    private final String host;
    private final String token;
    @Setter
    private String appName;
    @Setter
    private String versionName;
    @Setter
    private EnvType envType;

    private RequestService requestService;
    private JsonService jsonService;

    void init() {
        this.requestService = new RequestService(format(URL_PATTERN, host, API_VERSION), token);
        this.jsonService = new JsonService();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends DeepchecksClientInternalBuilder {
        Builder() {
            super();
        }

        @Override
        public DeepchecksClient build() {
            DeepchecksClient foo = super.build();
            foo.init();
            return foo;
        }
    }

    public DeepchecksClient(String token, String appName, String versionName) {
        this(DEFAULT_HOST, token, appName, versionName, DEFAULT_ENV_TYPE);
    }

    public DeepchecksClient(String host, String token, String appName, String versionName, EnvType envType) {
        this.host = host;
        this.token = token;
        this.appName = appName;
        this.versionName = versionName;
        this.envType = envType;
        this.requestService = new RequestService(format(URL_PATTERN, host, API_VERSION), token);
        this.jsonService = new JsonService();
        init();
    }

    public String logBatchInteractions(List<LogInteractionType> interactions) {
        InteractionRequest interactionRequest = toInteractionRequest(interactions);
        return requestService.post("interactions", jsonService.toJson(interactionRequest));
    }

    public String logInteraction(LogInteractionType interaction) {
        InteractionRequest interactionRequest = toInteractionRequest(asList(interaction));
        return requestService.post("interactions", jsonService.toJson(interactionRequest));
    }

    public String updateInteraction(String userInteractionId, String versionName, UpdateInteractionRequest request) {
        return requestService.put(format("application_versions/%s/interactions/%s", versionName, userInteractionId), jsonService.toJson(request));
    }

    public String getData() {
        DataRequest dataRequest = createDataRequest();
        return getData(dataRequest);
    }

    public String getData(DataRequest dataRequest) {
        return requestService.post("interactions-download-all-by-filter", jsonService.toJson(dataRequest));
    }

    private InteractionRequest toInteractionRequest(List<LogInteractionType> interactions) {
        return InteractionRequest.builder()
                .appName(appName)
                .versionName(versionName)
                .envType(envType)
                .interactions(interactions)
                .build();
    }

    private DataRequest createDataRequest() {
        return DataRequest.builder()
                .applicationVersionId(versionName)
                .environment(envType)
                .build();

    }

}
