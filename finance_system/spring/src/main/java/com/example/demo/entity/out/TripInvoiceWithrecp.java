package com.example.demo.entity.out;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TripInvoiceWithrecp {

    private Integer tripInvoiceId;

    private Integer usrId;

    private Integer fileId;

    private String startPlace;

    private String endPlace;

    private BigDecimal cost;

    private Date startTime;

    private Date endTime;

    private Integer invoiceReceiptsId;

    private String state;

    private Date createTime;

    private BigDecimal tax;

    private BigDecimal taxRate;

    private String tripCode;
}
