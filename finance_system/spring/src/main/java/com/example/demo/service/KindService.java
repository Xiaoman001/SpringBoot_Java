package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Base;
import com.example.demo.entity.Kind;
import com.example.demo.entity.in.BaseSelect;
import com.example.demo.entity.in.KindSelect;
import com.example.demo.mapper.KindMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KindService {

    @Autowired
    KindMapper kindMapper;

    public boolean insertKind(Kind kind){
        int result = kindMapper.insertKind(kind);
        return result == 1;
    }

    public boolean updateKind(Kind kind){
        int result = kindMapper.updateKind(kind);
        return result == 1;
    }

    public boolean deleteKindByKindId(Integer kineId){
        int result = kindMapper.deleteKind(kineId);
        return result == 1;
    }

    public IPage<Kind> queryKind(Page<Kind> page, KindSelect kind) {
        return kindMapper.queryKind(page, kind);
    }
}
