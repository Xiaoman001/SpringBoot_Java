package com.example.demo.service;

import com.example.demo.common.ImageUnit.PDFUnit;
import com.example.demo.common.exception.ApplicationException;
import com.example.demo.common.path.PathUtil;
import com.example.demo.common.uploadLocal.Upload;
import com.example.demo.entity.FileEntity;
import com.example.demo.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class FileService {

    @Value("${file.uploadFile}")
    private String uploadFilePath;

    @Value("${file.urlPath}")
    private String urlPath;

    @Autowired
    private FileMapper fileMapper;

    public Integer uploadFile(MultipartFile multipartFile) throws Exception{

        FileEntity fileEntity = new FileEntity();
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
            String filename = sdf.format((new Date()));
            String originFilename = multipartFile.getOriginalFilename();
            String suffix = originFilename.substring(originFilename.lastIndexOf(".") + 1);

            PathUtil pathUtil = new PathUtil();
            String path = pathUtil.getPath();
            path = path + uploadFilePath;
            path = URLDecoder.decode(path, "UTF-8");

            InputStream fileStream = multipartFile.getInputStream();
            if(suffix.equals("pdf")){
                fileStream = PDFUnit.getPDFStream(multipartFile);
                filename += ".png";
            }else {
                filename += "." + suffix;
            }

            //上传
            Upload.uploadFile(fileStream,filename,path);

            fileEntity.setFileAddress(urlPath + filename);
            int result = fileMapper.insertFile(fileEntity);
            if (result == 1){
                result = fileEntity.getFileId();
                return result;
            }else {
                File file = new File(path + filename);
                file.delete();
                throw new ApplicationException("上传失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApplicationException("上传失败");
        }
    }

    public String getFilePathById(Integer fileId){
        return fileMapper.queryFile(fileId);
    }
}
