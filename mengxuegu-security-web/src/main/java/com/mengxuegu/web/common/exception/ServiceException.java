package com.mengxuegu.web.common.exception;

import lombok.Data;

@Data
public class ServiceException extends RuntimeException {

    private int code = 500;

    private String msg ;

    public ServiceException() {

    }

    public ServiceException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(String msg) {
        this.msg = msg;
    }
}
