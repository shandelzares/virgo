package com.virgo.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultData<T> {
    private int status;
    private String message;
    private T data;

    public static <E> ResultData<E> success() {
        return new ResultData<>(200, "success", null);
    }

    public static <E> ResultData<E> fail(int status, String message) {
        return new ResultData<>(status, message, null);
    }

    public static <E> ResultData<E> fail(String message) {
        return new ResultData<>(500, message, null);
    }

    public static <E> ResultData<E> success(E page) {
        return new ResultData<>(200, "success", page);
    }
}
