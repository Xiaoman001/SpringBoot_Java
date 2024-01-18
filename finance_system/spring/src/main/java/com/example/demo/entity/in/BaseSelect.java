package com.example.demo.entity.in;

import lombok.Data;

import java.util.Date;

@Data
public class BaseSelect {
    private Integer baseId;

    private String city;

    private String address;

    private Integer page;

    private Integer pageSize;


}
