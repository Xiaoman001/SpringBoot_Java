package com.example.demo.entity;
import lombok.Data;

import java.util.Date;


@Data
public class User {
    private Integer usrId;

    private String password;

    private String name;

    private Integer baseId;

    private String department;

    private String grant;

    private Date lastSettleTime;

    private String role;

    private String userName;

    private Date applyTime;
}
