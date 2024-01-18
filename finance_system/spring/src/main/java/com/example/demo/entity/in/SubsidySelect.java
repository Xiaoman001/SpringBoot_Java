package com.example.demo.entity.in;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SubsidySelect {

    private Integer subsidyId;

    private String city;

    private BigDecimal cost;

    private Integer page;

    private Integer pageSize;


}
