package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.recurrence.BaseRest;
import com.example.demo.common.recurrence.HttpStatus;
import com.example.demo.common.recurrence.WebResponse;
import com.example.demo.entity.in.ObjectId;
import com.example.demo.entity.Notice;
import com.example.demo.entity.in.NoticeSelect;
import com.example.demo.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/notice")
@Api(tags = {"通知管理"})
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    @PostMapping("/addNotice")
    @ApiOperation(value = "增添通知")
    public WebResponse addNotice(@RequestBody @Validated Notice kind){

        WebResponse webResponse;
        try {
            boolean flag = noticeService.insertNotice(kind);
            webResponse = BaseRest.buildSuccessResponse(flag,"POST");
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildResponse(HttpStatus.ERROR,e.getMessage());
        }
        return webResponse;
    }

    @PostMapping("/updateNotice")
    @ApiOperation(value = "编辑通知")
    public WebResponse updateNotice(@RequestBody @Validated Notice kind){

        WebResponse webResponse;
        try {
            boolean flag = noticeService.updateNotice(kind);
            webResponse = BaseRest.buildSuccessResponse(flag,"POST");
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildResponse(HttpStatus.ERROR,e.getMessage());
        }
        return webResponse;
    }

    @PostMapping("/deleteNotice")
    @ApiOperation(value = "删除通知")
    public WebResponse deleteNotice(@RequestBody ObjectId id){

        WebResponse webResponse;
        try {
            boolean flag = noticeService.deleteNoticeByNoticeId(id.getId());
            webResponse = BaseRest.buildSuccessResponse(flag,"POST");
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildErrorResponse(e.getMessage());
        }
        return webResponse;
    }

    @PostMapping("/queryNotice")
    @ApiOperation(value = "查询通知信息")
    public WebResponse queryNotice(@RequestBody NoticeSelect noticeSelect){

        WebResponse webResponse;
        try {
            Page<Notice> pages = new Page<>(noticeSelect.getPage(), noticeSelect.getPageSize());
            webResponse = BaseRest.buildResponse(noticeService.queryNotice(pages, noticeSelect));
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildResponse(HttpStatus.ERROR,e.getMessage());
        }
        return webResponse;

    }

    @PostMapping("/queryNoticeNum")
    @ApiOperation(value = "查询某用户未读消息数")
    public WebResponse queryNoticeNum(Integer userId){
        try {
            Integer noticeNum = noticeService.queryNoReadCount(userId);
            return BaseRest.buildResponse(noticeNum);
        }catch (Exception e){
            return BaseRest.buildResponse(HttpStatus.ERROR, e.getMessage());
        }
    }
}
