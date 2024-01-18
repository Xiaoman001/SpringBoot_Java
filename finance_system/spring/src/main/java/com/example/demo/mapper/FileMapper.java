package com.example.demo.mapper;

import com.example.demo.entity.FileEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {

    int insertFile(FileEntity fileEntity);

    String queryFile(Integer fileId);
}
