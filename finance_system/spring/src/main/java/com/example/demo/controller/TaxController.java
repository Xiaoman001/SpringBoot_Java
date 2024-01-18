package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.recurrence.BaseRest;
import com.example.demo.common.recurrence.HttpStatus;
import com.example.demo.common.recurrence.WebResponse;
import com.example.demo.entity.in.ObjectId;
import com.example.demo.entity.Tax;
import com.example.demo.entity.in.TaxSelect;
import com.example.demo.service.TaxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/tax")
@Api(tags = {"税率"})
public class TaxController {

    @Autowired
    TaxService taxService;

    @PostMapping("/addTaxRate")
    @ApiOperation(value = "增添税率")
    public WebResponse addTaxRate(HttpServletRequest request, @RequestBody @Validated Tax tax){

        WebResponse webResponse;
        try {
            boolean flag = taxService.insertTaxRate(request, tax);
            webResponse = BaseRest.buildSuccessResponse(flag,"POST");
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildResponse(HttpStatus.ERROR,e.getMessage());
        }
        return webResponse;
    }

    @PostMapping("/updateTaxRate")
    @ApiOperation(value = "编辑税率")
    public WebResponse changeTaxRate(HttpServletRequest request, @RequestBody @Validated Tax tax){

        WebResponse webResponse;
        try {
            boolean flag = taxService.updateTaxRate(request, tax);
            webResponse = BaseRest.buildSuccessResponse(flag,"POST");
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildResponse(HttpStatus.ERROR,e.getMessage());
        }
        return webResponse;
    }

   @PostMapping("/deleteTaxRate")
    @ApiOperation(value = "删除税率")
    public WebResponse deleteTaxRate(HttpServletRequest request, @RequestBody ObjectId id){

        WebResponse webResponse;
        try {
            boolean flag = taxService.deleteTaxRate(request, id.getId());
            webResponse = BaseRest.buildSuccessResponse(flag,"POST");
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildErrorResponse(e.getMessage());
        }
        return webResponse;
    }

    @PostMapping("/queryTaxRate")
    @ApiOperation(value = "查询税率")
    public WebResponse queryTaxRate(@RequestBody TaxSelect tax){

        WebResponse webResponse;
        try {
            Page<Tax> pages = new Page<>(tax.getPage(), tax.getPageSize());
            webResponse = BaseRest.buildResponse(taxService.queryTaxRate(pages, tax));
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildErrorResponse(e.getMessage());
        }
        return webResponse;
    }

}
