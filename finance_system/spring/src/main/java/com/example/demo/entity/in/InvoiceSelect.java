package com.example.demo.entity.in;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class InvoiceSelect {

    private String invoiceReceiptsId;

    private String invoiceName;

    private Integer usrId;

    private String invoiceCode;

    private String invoiceNum;

    private Date beginDate;

    private Date endDate;

    private String soldTaxid;

    private String kind;

    private String invoiceKind;

    private Integer page;

    private Integer pageSize;


}
