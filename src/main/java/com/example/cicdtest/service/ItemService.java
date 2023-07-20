package com.example.cicdtest.service;

import com.example.cicdtest.dto.ItemRequestDto;
import com.example.cicdtest.dto.ItemResponseDto;
import com.example.cicdtest.entity.Item;
import com.example.cicdtest.entity.User;
import com.example.cicdtest.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public void createItem(ItemRequestDto itemRequestDto, MultipartFile image, User user) {
        String imagePath = null;
        if(!image.isEmpty()){
            try{
                imagePath = s3Upload.uploadFiles(image, "images");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Item item = new Item(itemRequestDto, imagePath, user);
        itemRepository.save(item);
    }


    public List<ItemResponseDto> getItems() {
        return itemRepository.findAll().stream().map(ItemResponseDto::new).toList();
    }

    public ItemResponseDto getItem(Long itemId, User user) {
        Item item = findItem(itemId);
        if(!item.getUser().getId().equals(user.getId())){
            throw new IllegalArgumentException("회원님이 삭성하신 글만 상세조회가 가능합니다.");
        }
        return new ItemResponseDto(item);
    }

    public Page<ItemResponseDto> getItemsInfiniteScroll(int page, int size, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Item> itemList = itemRepository.findAll(pageable);
        return itemList.map(ItemResponseDto::new);
    }

    @Transactional
    public void updateItem(Long itemId, ItemRequestDto itemRequestDto, MultipartFile image) {
        Item item = findItem(itemId);
        String imagePath = item.getImagePath();
        if(!image.isEmpty()){
            try{
                imagePath = s3Upload.uploadFiles(image, "images");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        item.updateItem(itemRequestDto, imagePath);
    }

    @Transactional
    public void updateItemPatch(Long itemId, ItemRequestDto itemRequestDto, MultipartFile image) {
        Item item = findItem(itemId);
        if(itemRequestDto != null){
            if(itemRequestDto.getContent() != null) item.setContent(itemRequestDto.getContent());
            if(itemRequestDto.getTitle() != null) item.setTitle(itemRequestDto.getTitle());
        }
        String imagePath = item.getImagePath();
        if(image != null){
            try{
                imagePath = s3Upload.uploadFiles(image, "images");
            } catch (Exception e) {
                e.printStackTrace();
            }
            item.setImagePath(imagePath);
        }
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
