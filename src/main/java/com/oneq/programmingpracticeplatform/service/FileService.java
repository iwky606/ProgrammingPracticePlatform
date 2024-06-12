package com.oneq.programmingpracticeplatform.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    int storeFile(MultipartFile file, long uploader);

    List<String> getIOFiles(List<Integer> ids);
}
