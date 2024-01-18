package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.jwt.JwtUtil;
import com.example.demo.entity.Notice;
import com.example.demo.entity.Receipts;
import com.example.demo.entity.Record;
import com.example.demo.entity.out.ReceiptsWithUser;
import com.example.demo.entity.in.ReceiptsIn;
import com.example.demo.mapper.ReceiptsMapper;
import com.example.demo.mapper.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class ReceiptsService {

    @Autowired
    ReceiptsMapper receiptsMapper;

    @Autowired
    NoticeService noticeService;

    @Autowired
    RecordMapper recordMapper;

    public int insertReceipts(HttpServletRequest request, Receipts receipts){
        int result = receiptsMapper.insertReceipts(receipts);
        Integer userId = JwtUtil.getUserInfo(request).getUsrId();
        recordMapper.insertRecord(new Record(0, receipts.getInvoiceReceiptsId(), userId
                , "新增报销单信息"+receipts, "报销单"));
        if (result == 1){
            return receipts.getInvoiceReceiptsId();
        }else{
            return 0;
        }
    }

    public boolean deleteReceipts(HttpServletRequest request, Integer invoiceReceiptsId){
        int result = receiptsMapper.deleteReceipts(invoiceReceiptsId);
        Integer userId = JwtUtil.getUserInfo(request).getUsrId();
        recordMapper.insertRecord(new Record(0, invoiceReceiptsId, userId
                , "删除报销单信息", "报销单"));
        return result == 1;
    }

    public boolean updateReceipts(HttpServletRequest request, Receipts receipts){
        if (receipts.getStates() != null ){
            if (receipts.getStates().equals("4")){
                Notice notice = new Notice();
                notice.setUsrId(receipts.getUsrId());
                notice.setNoticeStste("0");
                notice.setNote(receipts.getTitle()+"审核通过");
                noticeService.insertNotice(notice);
            }else if (receipts.getStates().equals("5")){
                Notice notice = new Notice();
                notice.setUsrId(receipts.getUsrId());
                notice.setNoticeStste("0");
                notice.setNote(receipts.getTitle()+"审核异常，请重新修改后上传");
                noticeService.insertNotice(notice);
            }
        }
        int result = receiptsMapper.updateReceipts(receipts);
        Integer userId = JwtUtil.getUserInfo(request).getUsrId();
        recordMapper.insertRecord(new Record(0, receipts.getInvoiceReceiptsId(), userId
                , "修改报销单信息"+receipts, "报销单"));
        return result == 1;
    }

    public IPage<ReceiptsWithUser> findReceipts(Page<ReceiptsWithUser> page, ReceiptsIn receipts){
        return receiptsMapper.findReceipts(page, receipts);
    }
}
