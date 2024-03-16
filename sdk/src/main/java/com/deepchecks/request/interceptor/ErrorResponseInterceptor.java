package com.deepchecks.request.interceptor;

import com.deepchecks.request.ErrorMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

public class ErrorResponseInterceptor implements Interceptor {
    
    public static final MediaType APPLICATION_JSON = MediaType.get("application/json; charset=utf-8");

    ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        
        if (!response.isSuccessful()) {
            String detail = "The response from the server was not OK";
            ResponseBody originalBody = response.body();
            if (originalBody != null) {
                detail = originalBody.string();
                originalBody.close();
            }
            String body = objectMapper.writeValueAsString(new ErrorMessage(response.code(), detail));
            ResponseBody responseBody = ResponseBody.create(body, APPLICATION_JSON);

            return response.newBuilder().body(responseBody).build();
        }
        return response;
    }
}