package com.example.demo.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Tax {

    private Integer taxRateId;

    private String taxRateType;

    private BigDecimal taxRate;

}