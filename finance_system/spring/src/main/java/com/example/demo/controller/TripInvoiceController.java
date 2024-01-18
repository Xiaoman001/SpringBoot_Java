package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.recurrence.BaseRest;
import com.example.demo.common.recurrence.HttpStatus;
import com.example.demo.common.recurrence.WebResponse;
import com.example.demo.entity.*;
import com.example.demo.entity.in.ObjectId;
import com.example.demo.entity.in.StringEneity;
import com.example.demo.entity.in.TripInvoiceSelect;
import com.example.demo.entity.out.TripInvoiceWithrecp;
import com.example.demo.service.TripInvoiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/tripInvoice")
@Api(tags = {"车票管理"})
public class TripInvoiceController {

    @Autowired
    TripInvoiceService tripInvoiceService;

    @PostMapping("/addTripInvoice")
    @ApiOperation(value = "增添车票信息")
    public WebResponse addTripInvoice(HttpServletRequest request, @RequestBody @Validated TripInvoice tripInvoice){

        WebResponse webResponse;
        try {
            webResponse = BaseRest.buildResponse(tripInvoiceService.insertTripInvoice(request, tripInvoice));
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildResponse(HttpStatus.ERROR,e.getMessage());
        }
        return webResponse;
    }

    @PostMapping("/updateTripInvoice")
    @ApiOperation(value = "编辑车票信息")
    public WebResponse updateTripInvoice(HttpServletRequest request, @RequestBody @Validated TripInvoice tripInvoice){

        WebResponse webResponse;
        try {
            boolean flag = tripInvoiceService.updateTripInvoice(request, tripInvoice);
            webResponse = BaseRest.buildSuccessResponse(flag,"POST");
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildResponse(HttpStatus.ERROR,e.getMessage());
        }
        return webResponse;
    }

    @PostMapping("/deleteTripInvoice")
    @ApiOperation(value = "删除车票信息")
    public WebResponse deleteTripInvoice(HttpServletRequest request, @RequestBody ObjectId id){

        WebResponse webResponse;
        try {
            boolean flag = tripInvoiceService.deleteTripInvoiceByTripInvoiceId(request, id.getId());
            webResponse = BaseRest.buildSuccessResponse(flag,"POST");
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildErrorResponse(e.getMessage());
        }
        return webResponse;
    }

    @PostMapping("/queryTripInvoice")
    @ApiOperation(value = "查询车票信息")
    public WebResponse queryTripInvoice(@RequestBody TripInvoiceSelect tripInvoiceSelect){

        WebResponse webResponse;
        try {
            Page<TripInvoiceWithrecp> pages = new Page<>(tripInvoiceSelect.getPage(), tripInvoiceSelect.getPageSize());
            webResponse = BaseRest.buildResponse(tripInvoiceService.queryTripInvoice(pages, tripInvoiceSelect));
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildResponse(HttpStatus.ERROR,e.getMessage());
        }
        return webResponse;
    }

    @PostMapping("/queryMoney")
    @ApiOperation(value = "查询报销金额")
    public WebResponse queryMoney(@RequestBody TripSelect tripSelect){
        WebResponse webResponse;
        try {
            Integer money = tripInvoiceService.queryMoney(tripSelect);
            webResponse = BaseRest.buildResponse(money);
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildResponse(HttpStatus.ERROR,e.getMessage());
        }
        return webResponse;
    }

    @PostMapping("/checkTrip")
    @ApiOperation(value = "车票查重")
    public WebResponse checkTrip(@RequestBody StringEneity number){
        boolean isMulti = tripInvoiceService.checkTrip(number.getNum())>0;
        return BaseRest.buildResponse(isMulti?"有重复":"无重复");
    }
}
