package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.recurrence.HttpStatus;
import com.example.demo.common.recurrence.BaseRest;
import com.example.demo.common.recurrence.WebResponse;
import com.example.demo.entity.Base;
import com.example.demo.entity.in.ObjectId;
import com.example.demo.entity.in.BaseSelect;
import com.example.demo.service.BaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController//controller必须要有的注解
@RequestMapping(value = "/base")//浏览器的访问路径：localhost:8080/base
@Api(tags = {"Base地管理"})
public class BaseController {

    @Autowired
    BaseService baseService;

    @PostMapping("/addBase")
    @ApiOperation(value = "增添Base地信息")
    public WebResponse addBase(HttpServletRequest request, @RequestBody @Validated Base base){

        WebResponse webResponse;
        try {
            boolean flag = baseService.insertBase(request, base);
            webResponse = BaseRest.buildSuccessResponse(flag,"POST");
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildResponse(HttpStatus.ERROR,e.getMessage());
        }
        return webResponse;
    }

    @PostMapping("/updateBase")
    @ApiOperation(value = "编辑Base地")
    public WebResponse updateBase(HttpServletRequest request, @RequestBody @Validated Base base){

        WebResponse webResponse;
        try {
            boolean flag = baseService.updateBase(request, base);
            webResponse = BaseRest.buildSuccessResponse(flag,"POST");
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildResponse(HttpStatus.ERROR,e.getMessage());
        }
        return webResponse;
    }

    @PostMapping("/deleteBase")
    @ApiOperation(value = "删除Base地")
    public WebResponse deleteBase(HttpServletRequest request, @RequestBody ObjectId id){

        WebResponse webResponse;
        try {
            boolean flag = baseService.deleteBaseByBaseId(request, id.getId());
            webResponse = BaseRest.buildSuccessResponse(flag,"POST");
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildErrorResponse(e.getMessage());
        }
        return webResponse;
    }

    @PostMapping("/queryBase")
    @ApiOperation(value = "查询Base地信息")
    public WebResponse queryBase(@RequestBody BaseSelect base){

        WebResponse webResponse;
        try {

            Page<Base> pages = new Page<>(base.getPage(), base.getPageSize());
            webResponse = BaseRest.buildResponse(baseService.queryBase(pages, base));
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildResponse(HttpStatus.ERROR ,e.getMessage());
        }
        return webResponse;
    }
}
