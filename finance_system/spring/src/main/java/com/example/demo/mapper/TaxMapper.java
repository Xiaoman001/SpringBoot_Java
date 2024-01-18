package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Kind;
import com.example.demo.entity.Tax;
import java.util.List;

import com.example.demo.entity.in.KindSelect;
import com.example.demo.entity.in.TaxSelect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TaxMapper {

    int insertTaxRate(Tax tax);
    int deleteTaxRate(Integer taxRateId);
    int updateTaxRate(Tax tax);

    IPage<Tax> queryTaxRate(Page<?> page, @Param(value = "tax") TaxSelect tax);

}