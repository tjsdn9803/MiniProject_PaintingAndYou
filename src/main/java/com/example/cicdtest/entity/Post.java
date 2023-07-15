package com.example.cicdtest.entity;

import com.example.cicdtest.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
@Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    public Post(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
    }

    public Post() {

    }
}
