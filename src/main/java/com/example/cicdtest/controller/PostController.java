package com.example.cicdtest.controller;

import com.example.cicdtest.dto.PostRequestDto;
import com.example.cicdtest.dto.PostResponseDto;
import com.example.cicdtest.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/api/post")
    private PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto){
        return postService.createPost(postRequestDto);
    }

    @GetMapping("/api/post")
    private List<PostResponseDto> getPosts(){
        return postService.getPosts();
    }
}
