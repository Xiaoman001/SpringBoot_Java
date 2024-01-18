package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.jwt.JwtUtil;
import com.example.demo.entity.NoInvoice;
import com.example.demo.entity.Record;
import com.example.demo.entity.in.NoInvoiceSelect;
import com.example.demo.entity.out.NoInvoiceWithRecp;
import com.example.demo.mapper.NoInvoiceMapper;
import com.example.demo.mapper.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class NoInvoiceService {

    @Autowired
    NoInvoiceMapper noInvoiceMapper;

    @Autowired
    RecordMapper recordMapper;

    public Integer insertNoInvoice(HttpServletRequest request, NoInvoice noInvoice){
        noInvoiceMapper.insertNoInvoice(noInvoice);
        Integer userId = JwtUtil.getUserInfo(request).getUsrId();
        recordMapper.insertRecord(new Record(0, noInvoice.getNoninvoiceId(), userId
                , "新增非发票信息"+noInvoice, "非发票"));
        return noInvoice.getNoninvoiceId();
    }

    public boolean updateNoInvoice(HttpServletRequest request, NoInvoice noInvoice){
        int result = noInvoiceMapper.updateNoInvoice(noInvoice);
        Integer userId = JwtUtil.getUserInfo(request).getUsrId();
        recordMapper.insertRecord(new Record(0, noInvoice.getNoninvoiceId(), userId
                , "修改非发票信息"+noInvoice, "非发票"));
        return result == 1;
    }

    public boolean deleteNoInvoiceByNoInvoiceId(HttpServletRequest request, Integer noInvoiceId){
        int result = noInvoiceMapper.deleteNoInvoice(noInvoiceId);
        Integer userId = JwtUtil.getUserInfo(request).getUsrId();
        recordMapper.insertRecord(new Record(0, noInvoiceId, userId
                , "新增非发票信息", "非发票"));
        return result == 1;
    }

    public IPage<NoInvoiceWithRecp> queryNoInvoice(Page<NoInvoiceWithRecp> page, NoInvoiceSelect noInvoice){
        return noInvoiceMapper.queryNoInvoice(page, noInvoice);
    }
}
