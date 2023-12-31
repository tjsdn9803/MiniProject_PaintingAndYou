package com.example.cicdtest.entity;

import com.example.cicdtest.dto.ItemRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Item extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 512)
    private String content;

    @Column
    private String imagePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Item(ItemRequestDto itemRequestDto, String imagePath, User user) {
        this.title = itemRequestDto.getTitle();
        this.content = itemRequestDto.getContent();
        this.imagePath = imagePath;
        this.user = user;
    }

    public void updateItem(ItemRequestDto itemRequestDto, String imagePath) {
        this.title = itemRequestDto.getTitle();
        this.content = itemRequestDto.getContent();
        this.imagePath = imagePath;
    }

}
