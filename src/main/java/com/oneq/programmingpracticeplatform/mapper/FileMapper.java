package com.oneq.programmingpracticeplatform.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO files (file_name, data,uploader,upload_time) VALUES (#{fileName}, #{data},#{uploader},#{time})")
    void insertFile(String fileName, byte[] data, long uploader, long time);
}
