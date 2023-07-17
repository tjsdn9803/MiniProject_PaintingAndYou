package com.example.cicdtest.dto;

import com.example.cicdtest.entity.Item;
import lombok.Getter;

@Getter
public class ItemResponseDto {

    private Long id;
    private String title;
    private String content;
    private String imagePath;

    public ItemResponseDto(Item item) {
        this.id = item.getId();
        this.title = item.getTitle();
        this.content = item.getContent();
        this.imagePath = item.getImagePath();
    }
}
