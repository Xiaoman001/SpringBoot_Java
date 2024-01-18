package com.example.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.jwt.PasswordGenerateUtil;
import com.example.demo.entity.*;
import com.example.demo.entity.in.BaseSelect;
import com.example.demo.entity.in.NoticeSelect;
import com.example.demo.entity.in.ReceiptsIn;
import com.example.demo.entity.out.ReceiptsWithUser;
import com.example.demo.mapper.BaseMapper;
import com.example.demo.mapper.LoginMapper;
import com.example.demo.mapper.NoticeMapper;
import com.example.demo.mapper.ReceiptsMapper;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
@RunWith(SpringRunner.class)
class DemoApplicationTests {

    @Autowired
    BaseMapper baseMapper;

    @Autowired
    LoginMapper loginMapper;

    @Autowired
    ReceiptsMapper receiptsMapper;

    @Autowired
    NoticeMapper noticeMapper;

    @Test
    void InsertNoticeTest() throws ParseException {
        Notice notice = new Notice();
        notice.setNoticeId(80);
        notice.setUsrId(1);
        notice.setNoticeStste("0");
        notice.setNote("00000");
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss");
        String txtDate = "2016-12-10 11:41:25";
        Date dt = dateFormat.parse(txtDate);
        notice.setTime(dt);
        noticeMapper.insertNotice(notice);
    }

    @Test
    void UpdateNoticeTest() throws ParseException {
        Notice notice = new Notice();
        notice.setNoticeId(80);
        notice.setUsrId(1);
        notice.setNoticeStste("1");
        notice.setNote("1111111");
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss");
        String txtDate = "2020-11-11 11:11:11";
        Date dt = dateFormat.parse(txtDate);
        notice.setTime(dt);
        noticeMapper.updateNotice(notice);
    }

    @Test
    void DeleteNoticeTest(){
        noticeMapper.deleteNotice(80);
    }

    @Test
    void QueryNoticeTest(){
        NoticeSelect noticeSelect = new NoticeSelect();
        Page<Notice> pages = new Page<>(1,20);
        noticeMapper.queryNotice(pages,noticeSelect);

    }
    @Test
    void CountNoReadTest(){
        noticeMapper.countNoRead(6);
    }

    @Test
    void InsertReceiptTest(){
        Receipts receipts = new Receipts();
        int invoiceReceiptsId = 99;
        receipts.setUsrId(1);
        receipts.setInvoiceReceiptsId(invoiceReceiptsId);
        receiptsMapper.insertReceipts(receipts);

    }

    @Test
    void UpdateReceiptTest() throws ParseException {
        Receipts receipts = new Receipts();
        int invoiceReceiptsId = 99;
        BigDecimal bg = BigDecimal.valueOf(200);
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss");
        String txtDate = "2016-12-10 11:41:25";
        Date dt = dateFormat.parse(txtDate);
        receipts.setUsrId(1);
        receipts.setApprover("李四");
        receipts.setTotalCost(bg);
        receipts.setStates("11");
        receipts.setApprovalNote("11111");
        receipts.setInvoiceReceiptsId(invoiceReceiptsId);
        receipts.setTaxSum(bg);
        receipts.setCreateTime(dt);
        receiptsMapper.updateReceipts(receipts);
    }

    @Test
    void DeleteReceiptTest(){
        receiptsMapper.deleteReceipts(99);
    }

    @Test
    void QueryReceiptTest(){
        ReceiptsIn receiptsIn = new ReceiptsIn();
        Page<ReceiptsWithUser> pages = new Page<>(1,20);
        receiptsMapper.findReceipts(pages,receiptsIn);

    }

    @Test
    void TokenTest(){
        final long EXPIRE_TIME = 60 * 60 * 1000 * 4 * 5;
        final String TOKEN_SECRET="DD5654D654DSD5S1D65S4D65S1D";
        User user = new User();
        user.setUserName("wangwu");
        user.setUsrId(1);

        JwtBuilder jwtBuilder = Jwts.builder();
        String token = jwtBuilder
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                .claim("username",user.getUserName())
                .claim("userid",user.getUsrId())
                .setSubject(user.getUserName())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE_TIME))
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256,TOKEN_SECRET)
                .compact();

        System.out.println(token);
    }
    @Test
    void selectTest(){
        User user = new User();
        loginMapper.selectUserByUser(user);
    }

    @Test
    void ChangePasswdTest(){
        ChangePasswdIn changePasswdIn = new ChangePasswdIn();
        changePasswdIn.setUserName("zhaoliu");
        changePasswdIn.setOriginPasswd("1");
        changePasswdIn.setNewPasswd("000");
        loginMapper.changePasswd(changePasswdIn);

    }

    @Test
    void QuaryInsertTest() {
        BaseSelect baseselect = new BaseSelect();
        Page<Base> pages = new Page<>(1,20);
        baseMapper.queryBase(pages,baseselect);
//        System.out.println(baseselect);

    }
    @Test
    void BaseInsertTest() {
        for(int i = 0; i < 3; i++){
            Base base = new Base();
            int baseId = 80;
            base.setAddress("" + i);
            base.setCity("成都" + i);
            base.setBaseId(baseId + i);
//        QueryWrapper<Base> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("address", "ss");
            baseMapper.insertBase(base);
        }
    }

    @Test
    void BaseUpdateTest(){
        for(int i = 0; i < 3; i++){
            Base base = new Base();
            int baseId = 80;
            base.setAddress("111");
            base.setCity("000");
            base.setBaseId(baseId + i);
            baseMapper.updateById(base);
        }

    }

    @Test
    void DeleteUpdateTest(){
        for(int i = 0; i < 3; i++){
            Base base = new Base();
            int baseId = 80;
            base.setAddress("" + i);
            base.setCity("成都" + i);
            base.setBaseId(baseId + i);
            baseMapper.deleteBase(baseId + i);
        }
    }


    @Test
    void getPasswd(){
        System.out.println(PasswordGenerateUtil.getPassword("zhangsan", "11111"));
        System.out.println(PasswordGenerateUtil.getPassword("lisi", "11111"));
        System.out.println(PasswordGenerateUtil.getPassword("wangwu", "11111"));
    }



    @Test
    void testPdf() throws Exception{
        List<byte[]> images = pdf2images(new File("E:/Fpdf/fp1.pdf"));
        System.out.println(images.size());
        FileOutputStream s = new FileOutputStream("E:/Fpdf/a.png");
        s.write(images.get(0));
        s.close();
    }

    private static List<byte[]> pdf2images(File pdfFile) throws Exception {
        //加载PDF
        PDDocument pdDocument = PDDocument.load(pdfFile);
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
