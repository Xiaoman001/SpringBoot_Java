package com.example.demo.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Subsidy;
import com.example.demo.entity.Tax;
import com.example.demo.entity.in.SubsidySelect;
import com.example.demo.entity.in.TaxSelect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SubsidyMapper {

    int deleteSubsidy(Integer subsidyId);

    int insertSubsidy(Subsidy subsidy);

    int updateSubsidy(Subsidy subsidy);

    IPage<Subsidy> findSubsidy(Page<?> page, @Param(value = "subsidy") SubsidySelect subsidy);
}
