package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.recurrence.HttpStatus;
import com.example.demo.common.recurrence.BaseRest;
import com.example.demo.common.recurrence.WebResponse;
import com.example.demo.entity.in.ObjectId;
import com.example.demo.entity.Subsidy;
import com.example.demo.entity.in.SubsidySelect;
import com.example.demo.service.SubsidyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/subsidy")
@Api(tags = {"差旅补助"})
public class SubsidyController {

    @Autowired
    SubsidyService subsidyService;

    @PostMapping("/addSubsidy")
    @ApiOperation(value = "增添差旅补助")
    public WebResponse addSubsidy(HttpServletRequest request, @RequestBody @Validated Subsidy subsidy){

        WebResponse webResponse;
        try {
            boolean flag = subsidyService.insertSubsidy(request, subsidy);
            webResponse = BaseRest.buildSuccessResponse(flag,"POST");
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildResponse(HttpStatus.ERROR,e.getMessage());
        }
        return webResponse;
    }

    @PostMapping("/updateSubsidy")
    @ApiOperation(value = "编辑差旅补助")
    public WebResponse changeSubsidy(HttpServletRequest request, @RequestBody @Validated Subsidy subsidy){

        WebResponse webResponse;
        try {
            boolean flag = subsidyService.updateSubsidy(request, subsidy);
            webResponse = BaseRest.buildSuccessResponse(flag,"POST");
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildResponse(HttpStatus.ERROR,e.getMessage());
        }
        return webResponse;
    }

    @PostMapping("/deleteSubsidy")
    @ApiOperation(value = "删除差旅补助")
    public WebResponse deleteSubsidyById(HttpServletRequest request, @RequestBody ObjectId id){

        WebResponse webResponse;
        try {
            boolean flag = subsidyService.deleteSubsidy(request, id.getId());
            webResponse = BaseRest.buildSuccessResponse(flag,"POST");
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildErrorResponse(e.getMessage());
        }
        return webResponse;
    }

    @PostMapping("/findSubsidy")
    @ApiOperation(value = "查询差旅补助")
    public WebResponse findSubsidy(@RequestBody SubsidySelect subsidy){

        WebResponse webResponse;
        try {
            Page<Subsidy> pages = new Page<>(subsidy.getPage(), subsidy.getPageSize());
            webResponse = BaseRest.buildResponse(subsidyService.findSubsidy(pages, subsidy));
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildErrorResponse(e.getMessage());
        }
        return webResponse;
    }

}
