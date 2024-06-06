package com.oneq.programmingpracticeplatform.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    int storeFile(MultipartFile file,long uploader) ;
}
