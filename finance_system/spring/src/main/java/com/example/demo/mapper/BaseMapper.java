package com.example.demo.mapper;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Base;
import com.example.demo.entity.in.BaseSelect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BaseMapper extends com.baomidou.mybatisplus.core.mapper.BaseMapper<Base> {

    int insertBase(Base base);

    int updateBase(Base base);

    int deleteBase(Integer baseId);

    IPage<Base> queryBase(Page<?> page, @Param(value = "base") BaseSelect base);
}
