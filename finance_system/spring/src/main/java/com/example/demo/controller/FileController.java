package com.example.demo.controller;

import com.example.demo.common.recurrence.BaseRest;
import com.example.demo.common.recurrence.WebResponse;
import com.example.demo.entity.in.ObjectId;
import com.example.demo.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/file")
@Api(tags = {"文件管理"})
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping("/uploadFile")
    @ApiOperation(value = "文件上传")
    public WebResponse uploadFile(@RequestParam(value = "file") MultipartFile file) {

        WebResponse webResponse;
        try {
            Integer result = fileService.uploadFile(file);
            webResponse = BaseRest.buildResponse(result);
        } catch (Exception e) {
            e.printStackTrace();
            webResponse = BaseRest.buildErrorResponse(e.getMessage());
        }
        return webResponse;
    }

    @PostMapping("/downLoadFile")
    @ApiOperation(value = "文件下载")
    public String downloadFile(@RequestBody ObjectId objectId) {
        return fileService.getFilePathById(objectId.getId());
    }
}
