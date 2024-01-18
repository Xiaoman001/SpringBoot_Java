package com.example.demo.common.uploadLocal;

import com.example.demo.common.exception.ApplicationException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Description 上传本地
 * @Author guanguan
 * @Date 2021/7/23 10:06
 * @Version 1.0
 */
@Component
public class Upload {


    public static void uploadFile(InputStream fileStream, String fileName,String uploadFilePath){

        new Upload().uploadImg(fileStream, fileName,uploadFilePath);
    }

    private void uploadImg(InputStream fileStream,String fileName,String uploadFilePath){

        try {
            File f = new File(uploadFilePath);
            if (!f.exists() && !f.isDirectory()){
                //创建文件夹
                f.mkdir();
            }
            //读取文件
            FileOutputStream fos = new FileOutputStream(uploadFilePath + fileName);
            //写入目标文件
            byte[] buffer = new byte[1024*1024];
            int byteRead = 0;
            while((byteRead= fileStream.read(buffer))!=-1){
                fos.write(buffer, 0, byteRead);
                fos.flush();
            }
            fos.close();
            fileStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApplicationException("上传失败");
        }
    }


}
