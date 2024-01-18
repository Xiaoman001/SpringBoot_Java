package com.example.demo.controller;


import com.example.demo.common.recurrence.BaseRest;
import com.example.demo.common.recurrence.HttpStatus;
import com.example.demo.common.recurrence.WebResponse;
import com.example.demo.entity.Base;
import com.example.demo.entity.InfoIn;
import com.example.demo.service.BaseService;
import com.example.demo.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/image")
@Api(tags = {"图片识别接口"})
public class ImageController {

    @Autowired
    ImageService imageService;

    @PostMapping("/getInfo")
    @ApiOperation(value = "获取凭证信息")
    public WebResponse addBase(@RequestBody InfoIn infoIn){

        WebResponse webResponse;
        try {
            Object res = imageService.getImageInfo(infoIn);
            webResponse = BaseRest.buildResponse(res);
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
}
