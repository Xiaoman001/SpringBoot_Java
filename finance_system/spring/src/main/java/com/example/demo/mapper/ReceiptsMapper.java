package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Receipts;
import com.example.demo.entity.out.ReceiptsWithUser;
import com.example.demo.entity.in.ReceiptsIn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReceiptsMapper {

    int insertReceipts(Receipts receipts);

    int deleteReceipts(Integer invoiceReceiptsId);

    int updateReceipts(Receipts receipts);

    IPage<ReceiptsWithUser> findReceipts(Page<?> page, @Param(value = "receiptsIn") ReceiptsIn receiptsIn);

}