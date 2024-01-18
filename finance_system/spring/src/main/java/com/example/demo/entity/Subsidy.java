package com.example.demo.entity;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class Subsidy {

    private Integer subsidyId;

    private String city;

    private BigDecimal cost;


}