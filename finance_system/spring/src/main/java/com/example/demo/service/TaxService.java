package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.jwt.JwtUtil;
import com.example.demo.entity.Kind;
import com.example.demo.entity.Record;
import com.example.demo.entity.Tax;
import com.example.demo.entity.in.KindSelect;
import com.example.demo.entity.in.TaxSelect;
import com.example.demo.mapper.RecordMapper;
import com.example.demo.mapper.TaxMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class TaxService {

    @Autowired
    TaxMapper taxMapper;

    @Autowired
    RecordMapper recordMapper;

    public boolean insertTaxRate(HttpServletRequest request, Tax tax){
        int result = taxMapper.insertTaxRate(tax);
        Integer userId = JwtUtil.getUserInfo(request).getUsrId();
        recordMapper.insertRecord(new Record(0, tax.getTaxRateId(), userId
                , "新增税金信息"+tax, "税金"));
        return result == 1;
    }

    public boolean updateTaxRate(HttpServletRequest request, Tax tax){
        int result = taxMapper.updateTaxRate(tax);
        Integer userId = JwtUtil.getUserInfo(request).getUsrId();
        recordMapper.insertRecord(new Record(0, tax.getTaxRateId(), userId
                , "更新税金信息"+tax, "税金"));
        return result == 1;
    }

    public boolean deleteTaxRate(HttpServletRequest request, Integer taxRateId){
        int result = taxMapper.deleteTaxRate(taxRateId);
        Integer userId = JwtUtil.getUserInfo(request).getUsrId();
        recordMapper.insertRecord(new Record(0, taxRateId, userId
                , "删除税金信息", "税金"));
        return result == 1;
    }

    public IPage<Tax> queryTaxRate(Page<Tax> page, TaxSelect tax) {
        return taxMapper.queryTaxRate(page, tax);
    }

}
