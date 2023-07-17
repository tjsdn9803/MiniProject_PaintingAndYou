package com.example.cicdtest.controller;

import com.example.cicdtest.dto.PostRequestDto;
import com.example.cicdtest.dto.PostResponseDto;
import com.example.cicdtest.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
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
