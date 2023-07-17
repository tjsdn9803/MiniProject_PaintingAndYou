package com.example.cicdtest.service;

import com.example.cicdtest.dto.ItemRequestDto;
import com.example.cicdtest.dto.ItemResponseDto;
import com.example.cicdtest.entity.Item;
import com.example.cicdtest.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public boolean createItem(ItemRequestDto itemRequestDto) {
        System.out.println("itemRequestDto.getTitle() = " + itemRequestDto.getTitle());
        Item item = new Item(itemRequestDto);
        System.out.println("item.getContent() = " + item.getContent());
        itemRepository.save(item);
        return true;
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
