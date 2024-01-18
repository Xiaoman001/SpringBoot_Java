package com.example.demo.entity;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Receipts {

    private Integer invoiceReceiptsId;
    private Integer usrId;
    private BigDecimal totalCost;
    private String states;
    private String approvalNote;
    private BigDecimal subsidyCost;
    private String approver;
    private String userNote;
    private Date createTime;
    private BigDecimal taxSum;
    private String title;
}