package com.example.cicdtest.service;

import com.example.cicdtest.dto.PostRequestDto;
import com.example.cicdtest.dto.PostResponseDto;
import com.example.cicdtest.entity.Post;
import com.example.cicdtest.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        Post post = new Post(postRequestDto);
        PostResponseDto postResponseDto = new PostResponseDto(postRepository.save(post));
        return postResponseDto;
    }

    public List<PostResponseDto> getPosts() {
        return postRepository.findAll().stream().map(PostResponseDto::new).toList();
    }
}
