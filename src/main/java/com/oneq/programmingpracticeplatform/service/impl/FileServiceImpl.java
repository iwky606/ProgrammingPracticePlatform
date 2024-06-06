package com.oneq.programmingpracticeplatform.service.impl;

import com.oneq.programmingpracticeplatform.mapper.FileMapper;
import com.oneq.programmingpracticeplatform.model.entity.File;
import com.oneq.programmingpracticeplatform.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {
    @Resource
    private FileMapper fileMapper;

    public void storeFile(MultipartFile file, long uploader) throws IOException {
        String fileName = file.getOriginalFilename();

        // 检查文件类型是否符合要求
        if (fileName == null || (!fileName.endsWith(".in") && !fileName.endsWith(".out"))) {
            throw new IllegalArgumentException("File type is not supported. Only .in and .out files are allowed.");
        }

        byte[] data = file.getBytes();
        long now = System.currentTimeMillis();

        // File fileEntity = new File(0L, fileName, data);
        fileMapper.insertFile(fileName,data, uploader, now);
    }
}
