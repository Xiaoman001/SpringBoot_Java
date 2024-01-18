package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.recurrence.HttpStatus;
import com.example.demo.common.recurrence.BaseRest;
import com.example.demo.common.recurrence.WebResponse;
import com.example.demo.entity.in.ObjectId;
import com.example.demo.entity.Receipts;
import com.example.demo.entity.out.ReceiptsWithUser;
import com.example.demo.entity.in.ReceiptsIn;
import com.example.demo.service.ReceiptsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/receipts")
@Api(tags = {"报销单"})
public class ReceiptsController {

    @Autowired
    ReceiptsService receiptsService;

    @PostMapping("/addReceipts")
    @ApiOperation(value = "增添报销单")
    public WebResponse add(HttpServletRequest request, @RequestBody @Validated Receipts receipts){

        WebResponse webResponse;
        try {
            Integer receiptsId = receiptsService.insertReceipts(request, receipts);
            webResponse = BaseRest.buildResponse(receiptsId);
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildResponse(HttpStatus.ERROR,e.getMessage());
        }
        return webResponse;
    }

    @PostMapping("/updateReceipts")
    @ApiOperation(value = "编辑报销单")
    public WebResponse change(HttpServletRequest request, @RequestBody @Validated Receipts receipts){

        WebResponse webResponse;
        try {
            boolean flag = receiptsService.updateReceipts(request, receipts);
            webResponse = BaseRest.buildSuccessResponse(flag,"POST");
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildResponse(HttpStatus.ERROR,e.getMessage());
        }
        return webResponse;
    }

    @PostMapping("/deleteReceipts")
    @ApiOperation(value = "删除报销单")
    public WebResponse delete(HttpServletRequest request, @RequestBody ObjectId id){

        WebResponse webResponse;
        try {
            boolean flag = receiptsService.deleteReceipts(request, id.getId());
            webResponse = BaseRest.buildSuccessResponse(flag,"POST");
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildErrorResponse(e.getMessage());
        }
        return webResponse;
    }

    @PostMapping("/findReceipts")
    @ApiOperation(value = "查询报销单")
    public WebResponse find(@RequestBody ReceiptsIn receipts){

        WebResponse webResponse;
        try {
            Page<ReceiptsWithUser> pages = new Page<>(receipts.getPage(), receipts.getPageSize());
            webResponse = BaseRest.buildResponse(receiptsService.findReceipts(pages, receipts));
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildErrorResponse(e.getMessage());
        }
        return webResponse;
    }

}
