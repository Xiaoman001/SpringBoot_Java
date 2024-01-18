package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Notice;
import com.example.demo.entity.in.NoticeSelect;
import com.example.demo.mapper.NoInvoiceMapper;
import com.example.demo.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {

    @Autowired
    NoticeMapper noticeMapper;

    public boolean insertNotice(Notice notice){
        int result = noticeMapper.insertNotice(notice);
        return result == 1;
    }

    public boolean updateNotice(Notice notice){
        int result = noticeMapper.updateNotice(notice);
        return result == 1;
    }

    public boolean deleteNoticeByNoticeId(Integer noticeId){
        int result = noticeMapper.deleteNotice(noticeId);
        return result == 1;
    }

    public IPage<Notice> queryNotice(Page<Notice> page, NoticeSelect noticeSelect){
        return noticeMapper.queryNotice(page, noticeSelect);
    }

    public Integer queryNoReadCount(Integer userId){
        return noticeMapper.countNoRead(userId);
    }
}
