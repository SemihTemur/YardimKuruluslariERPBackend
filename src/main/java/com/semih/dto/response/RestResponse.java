package com.semih.dto.response;

public class RestResponse<T> {
    private T data;
    private boolean status;
    private String message;

    public RestResponse(T data, boolean status) {
        this.data = data;
        this.status = status;
        this.message = status ? "Completed Succesfully" : "";
    }

    public static <T> RestResponse<T> of(T data) {
        return new RestResponse<T>(data, true);
    }

    public static <T> RestResponse<T> error(T data) {
        return new RestResponse<T>(data, false);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
