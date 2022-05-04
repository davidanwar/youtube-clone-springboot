package com.abs.youtubeclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoUploadResponse {

    private String videoId;
    private String videoUrl;
}
