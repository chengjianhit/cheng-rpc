package com.cheng.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Response {
//    private static final long serialVersionUID = -3458885875760034464L;

    private String requestId;

    private Object result;

    private String errMsg;

    private Throwable throwable;
}
