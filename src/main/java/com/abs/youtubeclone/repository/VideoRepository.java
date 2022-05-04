package com.abs.youtubeclone.repository;

import com.abs.youtubeclone.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<Video, String> {

}
