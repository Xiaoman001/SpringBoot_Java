package com.example.demo.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NoInvoice {
    private Integer noninvoiceId;

    private Integer usrId;

    private Integer fileId;

    private BigDecimal cost;

    private Integer invoiceReceiptsId;

    private String note;

    private String noninvoiceName;

    private String kind;

    private BigDecimal tax;

    private BigDecimal taxRate;
}
