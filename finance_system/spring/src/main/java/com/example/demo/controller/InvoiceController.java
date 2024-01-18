package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.recurrence.BaseRest;
import com.example.demo.common.recurrence.HttpStatus;
import com.example.demo.common.recurrence.WebResponse;
import com.example.demo.entity.in.ObjectId;
import com.example.demo.entity.Invoice;
import com.example.demo.entity.in.InvoiceSelect;
import com.example.demo.entity.in.StringEneity;
import com.example.demo.entity.out.InvoiceWithRecp;
import com.example.demo.service.InvoiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/invoice")
@Api(tags = {"发票管理"})
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @PostMapping("/addInvoice")
    @ApiOperation(value = "增添发票信息")
    public WebResponse addInvoice(HttpServletRequest request, @RequestBody @Validated Invoice invoice){

        WebResponse webResponse;
        try {
            webResponse = BaseRest.buildResponse(invoiceService.insertInvoice(request, invoice));
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().split(",")[0].equals("401") || e.getMessage().split(",")[0].equals("203")){
                webResponse = BaseRest.buildResponse(HttpStatus.UNAUTHORIZED,e.getMessage().split(",")[1]);
            }else {
                webResponse = BaseRest.buildResponse(HttpStatus.ERROR,e.getMessage());
            }
        }
        return webResponse;
    }

    @PostMapping("/updateInvoice")
    @ApiOperation(value = "编辑发票信息")
    public WebResponse updateInvoice(HttpServletRequest request, @RequestBody @Validated Invoice invoice){

        WebResponse webResponse;
        try {
            boolean flag = invoiceService.updateInvoice(request, invoice);
            webResponse = BaseRest.buildSuccessResponse(flag,"POST");
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().split(",")[0].equals("401")){
                webResponse = BaseRest.buildResponse(HttpStatus.UNAUTHORIZED,e.getMessage().split(",")[1]);
            }else {
                webResponse = BaseRest.buildResponse(HttpStatus.ERROR,e.getMessage());
            }
        }
        return webResponse;
    }

    @PostMapping("/deleteInvoice")
    @ApiOperation(value = "删除发票信息")
    public WebResponse deleteInvoice(HttpServletRequest request, @RequestBody ObjectId id){

        WebResponse webResponse;
        try {
            boolean flag = invoiceService.deleteInvoiceByInvoiceId(request, id.getId());
            webResponse = BaseRest.buildSuccessResponse(flag,"POST");
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().split(",")[0].equals("401")){
                webResponse = BaseRest.buildResponse(HttpStatus.UNAUTHORIZED,e.getMessage().split(",")[1]);
            }else {
                webResponse = BaseRest.buildErrorResponse(e.getMessage());
            }
        }
        return webResponse;
    }

    @PostMapping("/queryInvoice")
    @ApiOperation(value = "查询发票信息")
    public WebResponse queryInvoice(@RequestBody InvoiceSelect invoice){

        WebResponse webResponse;
        try {
            Page<InvoiceWithRecp> pages = new Page<>(invoice.getPage(), invoice.getPageSize());
            webResponse = BaseRest.buildResponse(invoiceService.queryInvoice(pages, invoice));
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildResponse(HttpStatus.ERROR ,e.getMessage());
        }
        return webResponse;

    }

    @PostMapping("/checkInvoice")
    @ApiOperation(value = "发票查重")
    public WebResponse checkInvoice(@RequestBody StringEneity number){
        boolean isMulti = invoiceService.checkInvoice(number.getNum())>0;
        return BaseRest.buildResponse(isMulti?"有重复":"无重复");
    }
}
