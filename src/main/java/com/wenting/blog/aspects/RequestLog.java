package com.wenting.blog.aspects;

import lombok.Data;
import lombok.Getter;

import java.util.Arrays;

/**
 *  日志对象
 */
@Getter
public class RequestLog{
    private String url;
    private String ip;
    private String classMethod;
    private Object[] args;

    @Override
    public String toString() {
        return "RequestLog{" +
                "url='" + url + '\'' +
                ", ip='" + ip + '\'' +
                ", classMethod='" + classMethod + '\'' +
                ", args=" +
                '}';
    }

    public RequestLog(String url, String ip, String classMethod, Object[] args) {
        this.url = url;
        this.ip = ip;
        this.classMethod = classMethod;
        this.args = args;
    }
}