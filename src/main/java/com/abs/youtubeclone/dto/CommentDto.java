package com.abs.youtubeclone.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {

    private String commentText;
    private String commentAuthor;
    private int likeCount;
    private int disLikeCount;
}
