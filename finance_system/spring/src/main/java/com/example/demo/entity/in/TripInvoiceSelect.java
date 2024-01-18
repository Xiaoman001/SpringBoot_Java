package com.example.demo.entity.in;

import lombok.Data;

import java.util.Date;

@Data
public class TripInvoiceSelect {

    private Integer invoiceReceiptsId;

    private Integer usrId;

    private String address;

    private Date beginDate;

    private Date endDate;

    private Integer page;

    private Integer pageSize;

}
