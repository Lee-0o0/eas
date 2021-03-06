package com.lee.eas.domain.dto;

import lombok.Data;

@Data
public class Response <T>{
    private int code;
    private String msg;
    private T data;
}
