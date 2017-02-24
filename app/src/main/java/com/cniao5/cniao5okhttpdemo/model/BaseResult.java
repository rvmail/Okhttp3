package com.cniao5.cniao5okhttpdemo.model;

/**
 * Created by Ivan on 2016/10/6.
 */

public class BaseResult {

    private int success;
    private String message;


    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
