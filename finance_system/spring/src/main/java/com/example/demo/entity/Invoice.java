package com.example.demo.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Invoice {

    private Integer invoiceId;

    private Integer usrId;

    private String invoiceReceiptsId;

    private String invoiceCode;

    private String invoiceNum;

    private BigDecimal cost;

    private Date invoiceTime;

    private String checkCode;

    private String soldTaxid;

    private String kind;

    private Integer fileId;

    private String invoiceKind;

    private String invoiceName;

    private String note;

    private BigDecimal tax;
}
