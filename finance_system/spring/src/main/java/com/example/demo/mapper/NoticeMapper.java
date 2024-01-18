package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Notice;
import com.example.demo.entity.in.NoticeSelect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface NoticeMapper {

    int insertNotice(Notice notice);

    int updateNotice(Notice notice);

    int deleteNotice(Integer noticeId);

    IPage<Notice> queryNotice(Page<?> page, @Param(value = "noticeSelect") NoticeSelect noticeSelect);

    Integer countNoRead(Integer userId);
}
