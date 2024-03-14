package com.deepchecks.request.interceptor;

import lombok.RequiredArgsConstructor;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
@RequiredArgsConstructor
public class DefaultHeaderInterceptor implements Interceptor {

    private final String headerKey;
    private final String headerValue;

    public Response intercept(Chain chain)
            throws IOException {

        Request originalRequest = chain.request();
        Request requestWithUserAgent = originalRequest
                .newBuilder()
                .header(headerKey, headerValue)
                .build();

        return chain.proceed(requestWithUserAgent);
    }
}
