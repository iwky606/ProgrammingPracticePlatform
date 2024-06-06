package com.oneq.programmingpracticeplatform.mapper;

import com.oneq.programmingpracticeplatform.model.entity.File;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO files (file_name, data,uploader,upload_time) VALUES (#{fileName}, #{data},#{uploader},#{time})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")    void insertFile(File file);
}
