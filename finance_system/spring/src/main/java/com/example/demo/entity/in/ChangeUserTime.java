package com.example.demo.entity.in;

import lombok.Data;

import java.util.Date;

@Data
public class ChangeUserTime {
    private Date applyTime;
    private Integer userId;
}
