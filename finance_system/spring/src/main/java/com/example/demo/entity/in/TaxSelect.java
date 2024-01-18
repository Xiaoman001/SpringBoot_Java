package com.example.demo.entity.in;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaxSelect {
    private Integer taxRateId;

    private String taxRateType;

    private BigDecimal taxRate;

    private Integer page;

    private Integer pageSize;


}
