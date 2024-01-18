package com.example.demo.entity.out;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class NoInvoiceWithRecp {
    private Integer noninvoiceId;

    private Integer usrId;

    private Integer fileId;

    private BigDecimal cost;

    private String note;

    private String noninvoiceName;

    private String kind;

    private String state;

    private Date createTime;

    private BigDecimal tax;

    private BigDecimal taxRate;

    private Integer invoiceReceiptsId;
}
