package com.example.demo.entity.out;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ReceiptsWithUser {

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

    private String name;
    private Integer baseId;
    private String department;
    private String grant;
    private Date lastSettleTime;
    private String role;
    private String userName;
    private Date applyTime;

}