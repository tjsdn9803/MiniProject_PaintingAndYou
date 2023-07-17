package com.example.cicdtest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Result<T> {
    private boolean ok;
    private T result;

    public static <T> Result<T> error(T errorCode){
        return new Result<>(false, errorCode);
    }
    public static <T> Result<T> success(T result){
        return new Result<>(true, result);
    }
}

