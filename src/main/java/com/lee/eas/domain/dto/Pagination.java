package com.lee.eas.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class Pagination <T>{
    private int page;
    private int pageSize;
    private int total;
    private List<T> data;
}
