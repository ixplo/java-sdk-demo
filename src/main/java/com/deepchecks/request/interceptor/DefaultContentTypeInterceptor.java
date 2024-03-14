package com.deepchecks.request;

import lombok.RequiredArgsConstructor;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
@RequiredArgsConstructor
public class DefaultContentTypeInterceptor implements Interceptor {

    private final String contentType;

    public Response intercept(Interceptor.Chain chain) throws IOException {

        Request originalRequest = chain.request();
        Request requestWithUserAgent = originalRequest
                .newBuilder()
                .header("Content-Type", contentType)
                .build();

        return chain.proceed(requestWithUserAgent);
    }
}
