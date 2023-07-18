package com.example.cicdtest.service;

import com.example.cicdtest.dto.ItemRequestDto;
import com.example.cicdtest.dto.ItemResponseDto;
import com.example.cicdtest.entity.Item;
import com.example.cicdtest.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final S3Upload s3Upload;

    public void createItem(ItemRequestDto itemRequestDto, MultipartFile image) {
        String imagePath = null;
        if(!image.isEmpty()){
            try{
                imagePath = s3Upload.uploadFiles(image, "images");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Item item = new Item(itemRequestDto, imagePath);
        itemRepository.save(item);
    }

    public List<ItemResponseDto> getItems() {
        return itemRepository.findAll().stream().map(ItemResponseDto::new).toList();
    }

    public ItemResponseDto getItem(Long itemId) {
        return new ItemResponseDto(findItem(itemId));
    }

    @Transactional
    public void updateItem(Long itemId, ItemRequestDto itemRequestDto) {
        Item item = findItem(itemId);
        item.updateItem(itemRequestDto);
    }

    @Transactional
    public void updateItemPatch(Long itemId, ItemRequestDto itemRequestDto) {
        Item item = findItem(itemId);
        if(itemRequestDto.getContent() != null) item.setContent(itemRequestDto.getContent());
        if(itemRequestDto.getTitle() != null) item.setTitle(itemRequestDto.getTitle());
    }

    public void deleteItem(Long itemId) {
        Item item = findItem(itemId);
        itemRepository.delete(item);
    }

    public Item findItem(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(()->
                new IllegalArgumentException("해당 id에 해당하는 아이템이 존재하지 않습니다."));
    }

}
