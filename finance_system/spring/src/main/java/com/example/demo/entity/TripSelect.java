package com.example.demo.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TripSelect {

    private Integer userId;

    private Date date;

    private Integer invoiceReceiptsId;

}
