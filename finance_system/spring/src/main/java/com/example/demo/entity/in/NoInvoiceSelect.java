package com.example.demo.entity.in;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NoInvoiceSelect {

    private Integer usrId;

    private Integer invoiceReceiptsId;

    private String noninvoiceName;

    private Integer page;

    private Integer pageSize;
}
