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

    public ItemResponseDto createItem(ItemRequestDto itemRequestDto, MultipartFile image) {
        String imagePath = null;
        if(!image.isEmpty()){
            try{
                imagePath = s3Upload.uploadFiles(image, "images");
            } catch (Exception e) {
                e.printStackTrace();
                Item item = new Item(itemRequestDto, e.getMessage());
                Item save = itemRepository.save(item);
                return new ItemResponseDto(save);
            }
        }
        Item item = new Item(itemRequestDto, imagePath);
        Item save = itemRepository.save(item);
        return new ItemResponseDto(save);
    }

    public List<ItemResponseDto> getItems() {
        return itemRepository.findAll().stream().map(ItemResponseDto::new).toList();
    }

    public ItemResponseDto getItem(Long itemId) {
        return new ItemResponseDto(findItem(itemId));
    }

    @Transactional
    public boolean updateItem(Long itemId, ItemRequestDto itemRequestDto) {
        Item item = findItem(itemId);
        item.updateItem(itemRequestDto);
        return true;
    }

    @Transactional
    public void updateItemPatch(Long itemId, ItemRequestDto itemRequestDto) {
        Item item = findItem(itemId);
        List<ItemRequestDto> itemRequestDtoList = new ArrayList<>();
        itemRequestDtoList.add(itemRequestDto);

        for(int i = 0 ; i < itemRequestDtoList.size(); i++){
            if(itemRequestDtoList.get(i).getContent() != null) item.setContent(itemRequestDtoList.get(i).getContent());
            if(itemRequestDtoList.get(i).getTitle() != null) item.setTitle(itemRequestDtoList.get(i).getTitle());
        }
    }

    public boolean deleteItem(Long itemId) {
        Item item = findItem(itemId);
        itemRepository.delete(item);
        return true;
    }

    public Item findItem(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(()->
                new IllegalArgumentException("해당 id에 해당하는 아이템이 존재하지 않습니다."));
    }


}
