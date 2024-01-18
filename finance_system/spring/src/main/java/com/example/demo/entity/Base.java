package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@TableName("TB_BASE")
public class Base {

    @TableId(value = "base_id")
    private Integer baseId;

    private String city;

    private String address;
}
