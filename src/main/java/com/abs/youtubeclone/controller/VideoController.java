package com.abs.youtubeclone.controller;

import com.abs.youtubeclone.dto.VideoDto;
import com.abs.youtubeclone.dto.VideoUploadResponse;
import com.abs.youtubeclone.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VideoUploadResponse uploadVideo(@RequestParam("file") MultipartFile file) {
        return videoService.uploadVideo(file);
    }

    @PostMapping("/thumbnail")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadVideo(@RequestParam("file") MultipartFile file, @RequestParam("videoId") String videoId) {
        return videoService.uploadThumbnail(file, videoId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public VideoDto editVideoMetaData(@RequestBody VideoDto videoDto) {
        return videoService.editVideo(videoDto);

    }

    @GetMapping("/{videoId}")
    @ResponseStatus(HttpStatus.OK)
    public VideoDto getVideoDetails(@PathVariable(value = "videoId") String videoId) {
        return videoService.getVideoDetails(videoId);
    }

    @PostMapping("/{videoId}/like")
    @ResponseStatus(HttpStatus.OK)
    public VideoDto likeVideo(@PathVariable(value = "videoId") String videoId) {
        return videoService.likeVideo(videoId);
    }
}
