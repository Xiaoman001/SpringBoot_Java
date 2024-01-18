package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.recurrence.BaseRest;
import com.example.demo.common.recurrence.HttpStatus;
import com.example.demo.common.recurrence.WebResponse;
import com.example.demo.entity.*;
import com.example.demo.entity.in.ObjectId;
import com.example.demo.entity.in.NoInvoiceSelect;
import com.example.demo.entity.out.NoInvoiceWithRecp;
import com.example.demo.service.NoInvoiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/noInvoice")
@Api(tags = {"非发票管理"})
public class NoInvoiceController {

    @Autowired
    NoInvoiceService noInvoiceService;

    @PostMapping("/addnoInvoice")
    @ApiOperation(value = "增添非发票信息")
    public WebResponse addnoInvoice(HttpServletRequest request, @RequestBody @Validated NoInvoice noInvoice){

        WebResponse webResponse;
        try {
            webResponse = BaseRest.buildResponse(noInvoiceService.insertNoInvoice(request, noInvoice));
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildResponse(HttpStatus.ERROR,e.getMessage());
        }
        return webResponse;
    }

    @PostMapping("/updatenoInvoice")
    @ApiOperation(value = "编辑非发票信息")
    public WebResponse updatenoInvoice(HttpServletRequest request, @RequestBody @Validated NoInvoice noInvoice){

        WebResponse webResponse;
        try {
            boolean flag = noInvoiceService.updateNoInvoice(request, noInvoice);
            webResponse = BaseRest.buildSuccessResponse(flag,"POST");
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildResponse(HttpStatus.ERROR,e.getMessage());
        }
        return webResponse;
    }

    @PostMapping("/deletenoInvoice")
    @ApiOperation(value = "删除非发票信息")
    public WebResponse deletenoInvoice(HttpServletRequest request, @RequestBody ObjectId id){

        WebResponse webResponse;
        try {
            boolean flag = noInvoiceService.deleteNoInvoiceByNoInvoiceId(request, id.getId());
            webResponse = BaseRest.buildSuccessResponse(flag,"POST");
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().split(",")[0].equals("401")){
                webResponse = BaseRest.buildResponse(HttpStatus.UNAUTHORIZED, e.getMessage().split(",")[1]);
            }else {
                webResponse = BaseRest.buildErrorResponse(e.getMessage());
            }
        }
        return webResponse;
    }

    @PostMapping("/queryNoInvoice")
    @ApiOperation(value = "查询非发票信息")
    public WebResponse queryNoInvoice(@RequestBody NoInvoiceSelect noInvoiceSelect){

        WebResponse webResponse;
        try {
            Page<NoInvoiceWithRecp> pages = new Page<>(noInvoiceSelect.getPage(), noInvoiceSelect.getPageSize());
            webResponse = BaseRest.buildResponse(noInvoiceService.queryNoInvoice(pages, noInvoiceSelect));
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildResponse(HttpStatus.ERROR,e.getMessage());
        }
        return webResponse;
    }
}
