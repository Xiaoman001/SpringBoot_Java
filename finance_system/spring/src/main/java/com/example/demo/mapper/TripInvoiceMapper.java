package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.*;
import com.example.demo.entity.in.TripInvoiceSelect;
import com.example.demo.entity.out.TripInvoiceWithrecp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TripInvoiceMapper {

    void insertTripInvoice(TripInvoice tripInvoice);

    int updateTripInvoice(TripInvoice tripInvoice);

    int deleteTripInvoice(Integer tripInvoiceId);

    IPage<TripInvoiceWithrecp> queryTripInvoice(Page<?> page,
                                                @Param(value = "tripInvoiceSelect") TripInvoiceSelect tripInvoiceSelect);

    List<TripRes> queryTrip(TripSelect tripSelect);

    Integer checkTrip(String num);
}
