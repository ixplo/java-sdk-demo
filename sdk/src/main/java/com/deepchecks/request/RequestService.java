package com.deepchecks.request;

import com.deepchecks.exception.DeepchecksClientException;
import com.deepchecks.request.interceptor.DefaultContentTypeInterceptor;
import com.deepchecks.request.interceptor.DefaultHeaderInterceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

import static java.lang.String.format;

public class RequestService {

    private final String baseUrl;
    private final OkHttpClient httpClient;

    public RequestService(String baseUrl, String apiToken) {
        this.baseUrl = baseUrl;
        this.httpClient = new OkHttpClient.Builder()
                .addInterceptor(new DefaultContentTypeInterceptor("application/json"))
                .addInterceptor(new DefaultHeaderInterceptor("Authorization", "Basic " + apiToken))
                .addInterceptor(new DefaultHeaderInterceptor("x-deepchecks-origin", "SDK"))
                .build();
    }

    public String get(String endpoint) {
        Request request = new Request.Builder()
                .url(createUrl(endpoint))
                .get()
                .build();
        return execute(request);
    }

    public String post(String endpoint, String body) {
        RequestBody requestBody = RequestBody.create(body, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(createUrl(endpoint))
                .post(requestBody)
                .build();
        return execute(request);
    }

    public String put(String endpoint, String body) {
        RequestBody requestBody = RequestBody.create(body, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(createUrl(endpoint))
                .put(requestBody)
                .build();
        return execute(request);
    }

    public String execute(Request request) {
        try (Response response = httpClient.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            throw new DeepchecksClientException("Http request failed: ", e);
        }
    }

    private String createUrl(String endpoint) {
        return format("%s/%s", baseUrl, endpoint);
    }
}
