package com.example.demo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.common.ImageUnit.ImageUnit;
import com.example.demo.common.path.PathUtil;
import com.example.demo.entity.InfoIn;
import com.example.demo.mapper.FileMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImageService {

    @Autowired
    FileMapper fileMapper;

    @Value("${file.uploadFile}")
    private String uploadFilePath;

    public Object getImageInfo(InfoIn infoIn) throws Exception{
        String path = new PathUtil().getPath();
        path = path + uploadFilePath;
        path = URLDecoder.decode(path, "UTF-8");
        path += fileMapper.queryFile(infoIn.getFileId()).split("/")[1];
        System.out.println(path);
        String res = ImageUnit.getInfo(path, infoIn.getType());
        JsonObject jsonObject = JsonParser.parseString (res).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("object_list");
        jsonObject = jsonArray.get(0).getAsJsonObject();
        jsonArray = jsonObject.getAsJsonArray("region_list");
        int size = jsonArray.size();
        JsonArray jsonArrayS;
        String value;
        String key;
        JSONObject resObject = new JSONObject();
        for (int i=0; i<size; i++){
            jsonObject = jsonArray.get(i).getAsJsonObject();
            jsonArrayS = jsonObject.getAsJsonArray("text_block_list");
            jsonObject = jsonArrayS.get(0).getAsJsonObject();
            value = jsonObject.get("value").getAsString();
            key = jsonObject.get("key").getAsString();
            key = key.replace("-", "_");
            resObject.put(key, value);
        }
        return resObject;
    }
}
