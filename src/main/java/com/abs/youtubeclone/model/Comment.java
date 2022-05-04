package com.abs.youtubeclone.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.concurrent.atomic.AtomicInteger;

@Document(value = "Comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    private String id;
    private String text;
    private String author;

    private AtomicInteger likeCount = new AtomicInteger(0);

    private AtomicInteger disLikeCount = new AtomicInteger(0);

    public int likeCount() {
        return likeCount.get();
    }

    public int disLikeCount() {
        return disLikeCount.get();
    }

}
