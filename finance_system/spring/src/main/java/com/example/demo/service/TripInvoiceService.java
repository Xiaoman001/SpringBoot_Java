package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.exception.ApplicationException;
import com.example.demo.common.jwt.JwtUtil;
import com.example.demo.entity.*;
import com.example.demo.entity.in.TripInvoiceSelect;
import com.example.demo.entity.out.TripInvoiceWithrecp;
import com.example.demo.mapper.RecordMapper;
import com.example.demo.mapper.TripInvoiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TripInvoiceService {

    @Autowired
    TripInvoiceMapper tripInvoiceMapper;

    @Autowired
    RecordMapper recordMapper;

    public Integer insertTripInvoice(HttpServletRequest request, TripInvoice tripInvoice){
        tripInvoiceMapper.insertTripInvoice(tripInvoice);
        Integer userId = JwtUtil.getUserInfo(request).getUsrId();
        recordMapper.insertRecord(new Record(0, tripInvoice.getTripInvoiceId(), userId
                , "新增车票信息"+tripInvoice, "车票"));
        return tripInvoice.getTripInvoiceId();
    }

    public boolean updateTripInvoice(HttpServletRequest request, TripInvoice tripInvoice){
        int result = tripInvoiceMapper.updateTripInvoice(tripInvoice);
        Integer userId = JwtUtil.getUserInfo(request).getUsrId();
        recordMapper.insertRecord(new Record(0, tripInvoice.getTripInvoiceId(), userId
                , "更新车票信息"+tripInvoice, "车票"));
        return result == 1;
    }

    public boolean deleteTripInvoiceByTripInvoiceId(HttpServletRequest request, Integer tripInvoiceId){
        int result = tripInvoiceMapper.deleteTripInvoice(tripInvoiceId);
        Integer userId = JwtUtil.getUserInfo(request).getUsrId();
        recordMapper.insertRecord(new Record(0, tripInvoiceId, userId
                , "删除车票信息", "车票"));
        return result == 1;
    }

    public IPage<TripInvoiceWithrecp> queryTripInvoice(Page<TripInvoiceWithrecp> page, TripInvoiceSelect tripInvoiceSelect){
        return tripInvoiceMapper.queryTripInvoice(page, tripInvoiceSelect);
    }

    public Integer queryMoney(TripSelect tripSelect){
        List<TripRes> tripInvoices = tripInvoiceMapper.queryTrip(tripSelect);
        Calendar aCalendar = Calendar.getInstance();
        Date time = tripInvoices.get(0).getStartDate();
        String lastPlace = tripInvoices.get(0).getStartPlace();
        int res = 0;
        for (TripRes tripInvoice : tripInvoices) {
            if (!tripInvoice.getStartPlace().equals(lastPlace)){
                throw new ApplicationException("路径不完整");
            }
            aCalendar.setTime(time);
            int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
            aCalendar.setTime(tripInvoice.getDate());
            int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
            res += (day2 - day1) * tripInvoice.getMoney();
            time = tripInvoice.getDate();
            lastPlace = tripInvoice.getEndPlace();
        }
        return res;
    }

    public Integer checkTrip(String num){
        return tripInvoiceMapper.checkTrip(num);
    }
}
