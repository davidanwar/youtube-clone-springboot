package com.abs.youtubeclone.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Document(value = "User")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String emailAddress;
    private Set<String> subscribers;
    private Set<String> subscribedToUsers;
    private List<String> videoHistory;
    private Set<String> likedVideo = ConcurrentHashMap.newKeySet(); // thread safe
    private Set<String> diskLikeVideo = ConcurrentHashMap.newKeySet();

    public void addToLikedVideos(String videoId) {
        likedVideo.add(videoId);
    }

    public void removeFromLikedVideos(String videoId) {
        likedVideo.remove(videoId);
    }

    public void removeFromDisLikedVideo(String videoId) {
        diskLikeVideo.remove(videoId);
    }
}
