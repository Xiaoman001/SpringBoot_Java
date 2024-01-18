package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Invoice;
import com.example.demo.entity.in.InvoiceSelect;
import com.example.demo.entity.out.InvoiceWithRecp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InvoiceMapper {

    void insertInvoice(Invoice invoice);

    int updateInvoice(Invoice invoice);

    int deleteInvoice(Integer invoiceId);

    IPage<InvoiceWithRecp> queryInvoice(Page<?> page, @Param(value = "invoice") InvoiceSelect invoice);

    int checkInvoice(String num);
}
