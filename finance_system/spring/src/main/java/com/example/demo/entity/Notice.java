package com.example.demo.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Notice {

    private Integer noticeId;

    private Integer usrId;

    private Date time;

    private String noticeStste;

    private String note;

}
