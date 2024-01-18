package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.jwt.JwtUtil;
import com.example.demo.entity.Record;
import com.example.demo.entity.Subsidy;
import com.example.demo.entity.Tax;
import com.example.demo.entity.in.SubsidySelect;
import com.example.demo.entity.in.TaxSelect;
import com.example.demo.mapper.RecordMapper;
import com.example.demo.mapper.SubsidyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class SubsidyService {

    @Autowired
    SubsidyMapper subsidyMapper;

    @Autowired
    RecordMapper recordMapper;

    public boolean insertSubsidy(HttpServletRequest request, Subsidy subsidy){
        int result = subsidyMapper.insertSubsidy(subsidy);
        Integer userId = JwtUtil.getUserInfo(request).getUsrId();
        recordMapper.insertRecord(new Record(0, subsidy.getSubsidyId(), userId
                , "新增差旅补助信息"+subsidy, "差旅补助"));
        return result == 1;
    }

    public boolean updateSubsidy(HttpServletRequest request, Subsidy subsidy){
        int result = subsidyMapper.updateSubsidy(subsidy);
        Integer userId = JwtUtil.getUserInfo(request).getUsrId();
        recordMapper.insertRecord(new Record(0, subsidy.getSubsidyId(), userId
                , "更新差旅补助信息"+subsidy, "差旅补助"));
        return result == 1;
    }

    public boolean deleteSubsidy(HttpServletRequest request, Integer subsidyId){
        int result = subsidyMapper.deleteSubsidy(subsidyId);
        Integer userId = JwtUtil.getUserInfo(request).getUsrId();
        recordMapper.insertRecord(new Record(0, subsidyId, userId
                , "删除差旅补助信息", "差旅补助"));
        return result == 1;
    }

    public IPage<Subsidy> findSubsidy(Page<Subsidy> page, SubsidySelect subsidy) {
        return subsidyMapper.findSubsidy(page, subsidy);
    }
}
