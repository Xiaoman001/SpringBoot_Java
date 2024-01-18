package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Base;
import com.example.demo.entity.Kind;
import com.example.demo.entity.in.BaseSelect;
import com.example.demo.entity.in.KindSelect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface KindMapper {

    int insertKind(Kind kind);

    int updateKind(Kind kind);

    int deleteKind(Integer kindId);

    IPage<Kind> queryKind(Page<?> page, @Param(value = "kind") KindSelect kind);
}
