package com.abs.youtubeclone.service;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public interface FileService {

    String uploadFile(MultipartFile file);
}
