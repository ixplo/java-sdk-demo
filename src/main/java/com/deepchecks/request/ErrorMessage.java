package com.deepchecks.request;

import lombok.Data;

@Data
public class ErrorMessage {

    private final int status;
    private final String detail;

    public ErrorMessage(int status, String detail) {
        this.status = status;
        this.detail = detail;
    }
    
}