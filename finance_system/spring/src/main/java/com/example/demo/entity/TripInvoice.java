package com.example.demo.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TripInvoice {

    private Integer tripInvoiceId;

    private Integer usrId;

    private Integer fileId;

    private String startPlace;

    private String endPlace;

    private BigDecimal cost;

    private Date startTime;

    private Date endTime;

    private String identify;

    private Integer invoiceReceiptsId;

    private BigDecimal tax;

    private BigDecimal taxRate;

    private String tripCode;
}
