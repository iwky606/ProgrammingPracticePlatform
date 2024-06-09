package com.oneq.programmingpracticeplatform.mapper;

import com.oneq.programmingpracticeplatform.model.entity.file.File;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO files (file_name, data,uploader,upload_time) VALUES (#{fileName}, #{data},#{uploader},#{time})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")    void insertFile(File file);

    List<String> findFiles(List<Integer> ids);
}
