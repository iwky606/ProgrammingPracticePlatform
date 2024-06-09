package com.oneq.programmingpracticeplatform.service.impl;

import com.oneq.programmingpracticeplatform.common.ErrorCode;
import com.oneq.programmingpracticeplatform.exception.BusinessException;
import com.oneq.programmingpracticeplatform.mapper.FileMapper;
import com.oneq.programmingpracticeplatform.model.entity.file.File;
import com.oneq.programmingpracticeplatform.service.FileService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class FileServiceImpl implements FileService {
    @Resource
    private FileMapper fileMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public int storeFile(MultipartFile file, long uploader) {
        String fileName = file.getOriginalFilename();

        // 检查文件类型是否符合要求
        if (fileName == null || (!fileName.endsWith(".in") && !fileName.endsWith(".out"))) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件名必须以.in或者.out结尾");
        }

        byte[] data;
        try {
            data = file.getBytes();
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "读取文件流失败");
        }
        long now = System.currentTimeMillis();
        File fileEntity = new File(0, fileName, data, uploader, now);
        fileMapper.insertFile(fileEntity);
        return fileEntity.getId();
    }

    /*
    * cacheTime单位为秒
    * */
    @Override
    public List<String> getFilesByIds(List<Integer> ids, long cacheTime) {
        if (cacheTime > 0) {
            Object res = redisTemplate.opsForValue().get(getCacheKey(ids));
            if (res != null) {
                return (List<String>) res;
            }
        }
        List<String> files = fileMapper.findFiles(ids);
        if (cacheTime > 0 && files != null && !files.isEmpty()) {
            redisTemplate.opsForValue().set(getCacheKey(ids), files, cacheTime, TimeUnit.SECONDS);
        }
        return files;
    }

    public String getCacheKey(List<Integer> ids) {
        return "file.cache." + ids.toString();
    }
}
