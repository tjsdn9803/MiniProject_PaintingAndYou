package com.example.cicdtest.controller;

import com.example.cicdtest.dto.ItemRequestDto;
import com.example.cicdtest.dto.ItemResponseDto;
import com.example.cicdtest.dto.Result;
import com.example.cicdtest.entity.User;
import com.example.cicdtest.security.UserDetailsImpl;
import com.example.cicdtest.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/api/item")
    private ResponseEntity<Result> createItem(@RequestPart("data") ItemRequestDto itemRequestDto,
                                              @RequestPart(required = false) MultipartFile image,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        itemService.createItem(itemRequestDto, image, user);
        return ResponseEntity.ok()
                .body(Result.success("생성 성공"));
    }

    @GetMapping("/api/item")
    private ResponseEntity<Result> getItems(){
        List<ItemResponseDto> items = itemService.getItems();
        return ResponseEntity.ok()
                .body(Result.success(items));
    }

    @GetMapping("/api/item/{itemId}")
    private ResponseEntity<Result> getItem(@PathVariable Long itemId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        ItemResponseDto item = itemService.getItem(itemId, user);
        return ResponseEntity.ok()
                .body(Result.success(item));
    }

    @GetMapping("/api/item/scroll")
    private ResponseEntity<Result> getItemsInfiniteScroll(int page, int size, boolean isAsc){
        Page<ItemResponseDto> itemsInfiniteScroll = itemService.getItemsInfiniteScroll(page-1, size, isAsc);
        return ResponseEntity.ok()
                .body(Result.success(itemsInfiniteScroll));
    }

    @PutMapping("/api/item/{itemId}")
    private ResponseEntity<Result> updateItem(@PathVariable Long itemId,
                                              @RequestPart("data") ItemRequestDto itemRequestDto,
                                              @RequestPart(required = false) MultipartFile image){
        itemService.updateItem(itemId, itemRequestDto, image);
        return ResponseEntity.ok()
                .body(Result.success("수정 성공"));
    }

    @PatchMapping("/api/item/{itemId}")
    public ResponseEntity<Result> updateItemPatch(@PathVariable Long itemId,
                                                  @RequestPart(value = "data", required = false) ItemRequestDto itemRequestDto,
                                                  @RequestPart(required = false) MultipartFile image){
        itemService.updateItemPatch(itemId, itemRequestDto, image);
        return ResponseEntity.ok()
                .body(Result.success("수정 성공"));
    }

    @DeleteMapping("/api/item/{itemId}")
    private ResponseEntity<Result> deleteItem(@PathVariable Long itemId){
        itemService.deleteItem(itemId);
        return ResponseEntity.ok()
                .body(Result.success("삭제 성공"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Result> exceptionHandler(Exception e){
        return ResponseEntity.badRequest()
                .body(Result.error(e.getMessage()));
    }

}
