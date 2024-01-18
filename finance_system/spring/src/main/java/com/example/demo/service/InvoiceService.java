package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.jwt.JwtUtil;
import com.example.demo.entity.Invoice;
import com.example.demo.entity.Record;
import com.example.demo.entity.in.InvoiceSelect;
import com.example.demo.entity.out.InvoiceWithRecp;
import com.example.demo.mapper.InvoiceMapper;
import com.example.demo.mapper.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class InvoiceService {

    @Autowired
    InvoiceMapper invoiceMapper;

    @Autowired
    RecordMapper recordMapper;

    public Integer insertInvoice(HttpServletRequest request, Invoice invoice){
        invoiceMapper.insertInvoice(invoice);
        Integer userId = JwtUtil.getUserInfo(request).getUsrId();
        recordMapper.insertRecord(new Record(0, invoice.getInvoiceId(), userId
                , "新增发票信息:"+invoice, "发票"));
        return invoice.getInvoiceId();
    }

    public boolean updateInvoice(HttpServletRequest request, Invoice invoice){
        int result = invoiceMapper.updateInvoice(invoice);
        Integer userId = JwtUtil.getUserInfo(request).getUsrId();
        recordMapper.insertRecord(new Record(0, invoice.getInvoiceId(), userId
                , "修改发票信息:"+invoice, "发票"));
        return result == 1;
    }

    public boolean deleteInvoiceByInvoiceId(HttpServletRequest request, Integer invoiceId){
        int result = invoiceMapper.deleteInvoice(invoiceId);
        Integer userId = JwtUtil.getUserInfo(request).getUsrId();
        recordMapper.insertRecord(new Record(0, invoiceId, userId
                , "删除发票信息", "发票"));
        return result == 1;
    }

    public IPage<InvoiceWithRecp> queryInvoice(Page<InvoiceWithRecp> page, InvoiceSelect invoice){
        return invoiceMapper.queryInvoice(page, invoice);
    }

    public Integer checkInvoice(String num){
        return invoiceMapper.checkInvoice(num);
    }
}
