package com.example.fitgo.controller.response;

public class SuccessResponse {
    private Object message;
    private int status;

    public SuccessResponse(Object message, int status) {
        this.message = message;
        this.status = status;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
