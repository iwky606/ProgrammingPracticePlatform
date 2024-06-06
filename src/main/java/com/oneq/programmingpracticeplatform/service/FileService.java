package com.oneq.programmingpracticeplatform.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    int storeFile(MultipartFile file,long uploader) ;
}
