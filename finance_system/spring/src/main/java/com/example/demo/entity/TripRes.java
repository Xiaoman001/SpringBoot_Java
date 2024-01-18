package com.example.demo.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TripRes {

    private Date date;

    private Date startDate;

    private Integer money;

    private String startPlace;

    private String endPlace;

}
