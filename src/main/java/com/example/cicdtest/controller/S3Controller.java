package com.example.cicdtest.controller;

import com.example.cicdtest.dto.Result;
import com.example.cicdtest.service.S3Upload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class S3Controller {

    private final S3Upload s3Upload;

    @PostMapping("/api/image")
    public ResponseEntity<String> imageUpload(@RequestPart(required = false) MultipartFile multipartFile) {
        if(multipartFile.isEmpty()) {
            return ResponseEntity.ok()
                    .body("파일이 유효하지 않습니다.");
        }
        try {
            s3Upload.uploadFiles(multipartFile, "static");
            return ResponseEntity.ok()
                    .body("파일이 성공적으로 저장되었습니다.");
        } catch (Exception e){
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result> exceptionHandler(Exception e){
        return ResponseEntity.badRequest()
                .body(Result.error(e.getMessage()));
    }
}
