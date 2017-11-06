package io.harry.zealotboot.response;

import lombok.Getter;

@Getter
public class ZealotResponse<T> {
    private T data;

    public ZealotResponse(T data) {
        this.data = data;
    }
}
