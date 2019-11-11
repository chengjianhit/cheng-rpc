package com.cheng.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Request {
//    private static final long serialVersionUID = -2001160518208675889L;

    private String requestId;

    private String methodName;

    private String className;


    private Object[] args;

    private Class<?>[] parameterTypes;
}
