package com.abs.youtubeclone.service;

import com.abs.youtubeclone.dto.CommentDto;
import com.abs.youtubeclone.dto.VideoDto;
import com.abs.youtubeclone.dto.VideoUploadResponse;
import com.abs.youtubeclone.mapper.CommentMapper;
import com.abs.youtubeclone.model.Video;
import com.abs.youtubeclone.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Service
public class VideoService {

    @Autowired
    private S3Service s3Service;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentMapper commentMapper;

    public VideoUploadResponse uploadVideo(MultipartFile file) {
        String videoUrl = s3Service.uploadFile(file);
        var video = new Video();
        video.setVideoUrl(videoUrl);
        Video videoSaved = videoRepository.save(video);
        return new VideoUploadResponse(videoSaved.getId(), videoSaved.getVideoUrl());
    }

    public VideoDto editVideo(VideoDto videoDto) {

        Video savedVideo = getVideo(videoDto.getId());
        savedVideo.setTitle(videoDto.getTitle());
        savedVideo.setDescription(videoDto.getDescription());
        savedVideo.setTags(videoDto.getTags());
        savedVideo.setThumbnailUrl(videoDto.getThumbnailUrl());
        savedVideo.setVideoStatus(videoDto.getVideoStatus());
        videoRepository.save(savedVideo);
        return videoDto;
    }

    public String uploadThumbnail(MultipartFile file, String videoId) {
        Video videoSaved = getVideo(videoId);
        String thumbnailUrl = s3Service.uploadFile(file);
        videoSaved.setThumbnailUrl(thumbnailUrl);
        videoRepository.save(videoSaved);
        return thumbnailUrl;
    }

    private Video getVideo(String videoId) {
        return videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("Can't find video by id - " + videoId));
    }

    public VideoDto getVideoDetails(String videoId) {
        Video videoSaved = getVideo(videoId);
        VideoDto videoDto = new VideoDto();
        videoDto.setVideoUrl(videoSaved.getVideoUrl());
        videoDto.setTitle(videoSaved.getTitle());
        videoDto.setThumbnailUrl(videoSaved.getThumbnailUrl());
        videoDto.setId(videoSaved.getId());
        videoDto.setDescription(videoSaved.getDescription());
        videoDto.setTags(videoSaved.getTags());
        videoDto.setVideoStatus(videoSaved.getVideoStatus());
        return videoDto;
    }

    public VideoDto likeVideo(String videoId) {
        Video videoSaved = getVideo(videoId);

        if (userService.ifLikedVideo(videoId)) {
            videoSaved.decrementLike();
            userService.removeFromLikedVideos(videoId);
        } else if (userService.ifDisLikedVideo(videoId)) {
            videoSaved.decrementDislike();
            userService.removeFromDisLikedVideo(videoId);
            videoSaved.incrementLike();
            userService.addToLikedVideos(videoId);
        } else {
            videoSaved.incrementLike();
            userService.addToLikedVideos(videoId);
        }

        videoRepository.save(videoSaved);

        VideoDto videoDto = new VideoDto();
        videoDto.setVideoUrl(videoSaved.getVideoUrl());
        videoDto.setTitle(videoSaved.getTitle());
        videoDto.setThumbnailUrl(videoSaved.getThumbnailUrl());
        videoDto.setId(videoSaved.getId());
        videoDto.setDescription(videoSaved.getDescription());
        videoDto.setTags(videoSaved.getTags());
        videoDto.setVideoStatus(videoSaved.getVideoStatus());
        videoDto.setLikeCount(videoSaved.getLikes().get());
        videoDto.setDisLikeCount(videoSaved.getDisLikes().get());
        return videoDto;

    }

    public void  addComment(CommentDto commentDto, String videoId) {
        var video = getVideo(videoId);
        var comment = commentMapper.mapFromDto(commentDto);
        video.addComment(comment);
        videoRepository.save(video);
    }

    public List<CommentDto> getAllComments(String videoId) {
        return videoRepository.findById(videoId)
                .stream()
                .map(video -> commentMapper.mapToDtoList(video.getComments()))
                .findAny().orElse(Collections.emptyList());
    }
}
