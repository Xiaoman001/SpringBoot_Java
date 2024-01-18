package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.recurrence.BaseRest;
import com.example.demo.common.recurrence.HttpStatus;
import com.example.demo.common.recurrence.WebResponse;
import com.example.demo.entity.in.ObjectId;
import com.example.demo.entity.Kind;
import com.example.demo.entity.in.KindSelect;
import com.example.demo.service.KindService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/kind")
@Api(tags = {"报销种类管理"})
public class KindController {

    @Autowired
    KindService kindService;

    @PostMapping("/addKind")
    @ApiOperation(value = "增添报销种类信息")
    public WebResponse addKind(@RequestBody @Validated Kind kind){

        WebResponse webResponse;
        try {
            boolean flag = kindService.insertKind(kind);
            webResponse = BaseRest.buildSuccessResponse(flag,"POST");
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

    @PostMapping("/updateKind")
    @ApiOperation(value = "编辑报销种类")
    public WebResponse updateBase(@RequestBody @Validated Kind kind){

        WebResponse webResponse;
        try {
            boolean flag = kindService.updateKind(kind);
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

    @PostMapping("/deleteKind")
    @ApiOperation(value = "删除报销种类")
    public WebResponse deleteBase(@RequestBody ObjectId id){
        WebResponse webResponse;
        try {
            boolean flag = kindService.deleteKindByKindId(id.getId());
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

    @PostMapping("/queryKind")
    @ApiOperation(value = "查询报销种类信息")
    public WebResponse queryBase(@RequestBody KindSelect kind){

        WebResponse webResponse;
        try {
            Page<Kind> pages = new Page<>(kind.getPage(), kind.getPageSize());
            webResponse = BaseRest.buildResponse(kindService.queryKind(pages, kind));
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().split(",")[0].equals("401") || e.getMessage().split(",")[0].equals("403")){
                webResponse = BaseRest.buildResponse(HttpStatus.UNAUTHORIZED,e.getMessage().split(",")[1]);
            }else {
                webResponse = BaseRest.buildResponse(HttpStatus.ERROR ,e.getMessage());
            }
        }
        return webResponse;
    }
}
