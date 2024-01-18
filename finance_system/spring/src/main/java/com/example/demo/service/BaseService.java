package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.jwt.JwtUtil;
import com.example.demo.entity.Base;
import com.example.demo.entity.Record;
import com.example.demo.entity.in.BaseSelect;
import com.example.demo.mapper.BaseMapper;
import com.example.demo.mapper.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class BaseService {

    @Autowired
    BaseMapper baseMapper;

    @Autowired
    RecordMapper recordMapper;

    public boolean insertBase(HttpServletRequest request, Base base) {
        int result = baseMapper.insertBase(base);
        Integer userId = JwtUtil.getUserInfo(request).getUsrId();
        recordMapper.insertRecord(new Record(0, base.getBaseId(), userId
                , "新增base地信息"+base, "base地"));
        return result == 1;
    }

    public boolean updateBase(HttpServletRequest request, Base base) {
        int result = baseMapper.updateBase(base);
        Integer userId = JwtUtil.getUserInfo(request).getUsrId();
        recordMapper.insertRecord(new Record(0, base.getBaseId(), userId
                , "修改base地信息"+base, "base地"));
        return result == 1;
    }

    public boolean deleteBaseByBaseId(HttpServletRequest request, Integer baseId) {
        int result = baseMapper.deleteBase(baseId);
        Integer userId = JwtUtil.getUserInfo(request).getUsrId();
        recordMapper.insertRecord(new Record(0, baseId, userId
                , "删除base地信息", "base地"));
        return result == 1;
    }


    public IPage<Base> queryBase(Page<Base> page, BaseSelect base) {
        return baseMapper.queryBase(page, base);
    }
}