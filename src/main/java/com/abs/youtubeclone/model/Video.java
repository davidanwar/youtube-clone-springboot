package com.abs.youtubeclone.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Document(value = "Video")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {

    @Id
    private String id;
    private String title;
    private String description;
    private String userId;
    private AtomicInteger likes = new AtomicInteger(0);
    private AtomicInteger disLikes = new AtomicInteger(0);
    private Set<String> tags;
    private String videoUrl;
    private VideoStatus videoStatus;
    private Integer viewCount;
    private String thumbnailUrl;
    private List<Comment> comments = new ArrayList<>();


    public void incrementLike() {
        likes.incrementAndGet();
    }

    public void decrementLike() {
        likes.decrementAndGet();
    }

    public void incrementDislike() {
        disLikes.incrementAndGet();
    }

    public void decrementDislike() {
        disLikes.decrementAndGet();
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

}
