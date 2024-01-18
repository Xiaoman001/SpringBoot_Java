package com.example.demo.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Record {

    private Integer opId;

    private Integer targetId;

    private Integer usrId;

    private String note;

    private String opType;

    public Record(Integer opId, Integer targetId, Integer usrId, String note, String opType) {
        this.opId = opId;
        this.targetId = targetId;
        this.usrId = usrId;
        this.note = note;
        this.opType = opType;
    }
}
