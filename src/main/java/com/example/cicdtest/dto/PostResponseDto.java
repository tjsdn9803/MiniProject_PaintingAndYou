package com.example.cicdtest.dto;

import com.example.cicdtest.entity.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {

    private Long id;
    private String title;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
    }
}
