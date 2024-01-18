package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.*;
import com.example.demo.entity.in.NoInvoiceSelect;
import com.example.demo.entity.out.NoInvoiceWithRecp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface NoInvoiceMapper {

    void insertNoInvoice(NoInvoice noInvoice);

    int updateNoInvoice(NoInvoice noInvoice);

    int deleteNoInvoice(Integer noInvoiceId);

    IPage<NoInvoiceWithRecp> queryNoInvoice(Page<?> page, @Param(value = "noInvoice") NoInvoiceSelect noInvoice);
}
