package com.example.demo.common.ImageUnit;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PDFUnit {

    public static InputStream getPDFStream(MultipartFile file) throws Exception{
        List<byte[]> images = pdf2images(file.getInputStream());
        return new ByteArrayInputStream(images.get(0));
    }

    private static List<byte[]> pdf2images(InputStream input) throws Exception {
        //加载PDF
        PDDocument pdDocument = PDDocument.load(input);
        //创建PDF渲染器
        PDFRenderer renderer = new PDFRenderer(pdDocument);
        int pages = pdDocument.getNumberOfPages();
        List<byte[]> images = new ArrayList<>();
        for (int i = 0; i < pages; i++) {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            //将PDF的每一页渲染成一张图片
            BufferedImage image = renderer.renderImage(i, 2);
            ImageIO.write(image, "png", output);
            images.add(output.toByteArray());
        }
        pdDocument.close();
        return images;
    }
}
