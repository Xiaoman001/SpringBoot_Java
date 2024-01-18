package com.example.demo.entity.in;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ReceiptsIn {

    private Integer invoiceReceiptsId;
    private Integer usrId;
    private BigDecimal totalCost;
    private String states;
    private String approvalNote;
    private BigDecimal subsidyCost;
    private String title;
    private String approval;
    private String userNote;
    private Date beginTime;
    private Date endTime;
    private BigDecimal taxSum;
    private String department;
    private Integer page;
    private Integer pageSize;
}