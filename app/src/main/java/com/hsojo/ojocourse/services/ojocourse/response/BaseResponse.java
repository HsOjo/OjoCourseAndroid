package com.hsojo.ojocourse.services.ojocourse.response;

import android.annotation.SuppressLint;

public class BaseResponse<T> {
    public T data;
    public int error;
    public String msg;
    public String exc;

    public BaseResponse(T data, int error, String msg, String exc) {
        this.data = data;
        this.error = error;
        this.msg = msg;
        this.exc = exc;
    }

    @SuppressLint("DefaultLocale")
    public String getMessage() {
        return String.format("[%d] %s", this.error, this.msg.equals("") ? this.exc : this.msg);
    }
}
