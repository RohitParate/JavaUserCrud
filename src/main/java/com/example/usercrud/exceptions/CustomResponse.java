package com.example.usercrud.exceptions;

import lombok.*;

@Getter
@Setter
@Builder
@Data
//@RequiredArgsConstructor
//@NoArgsConstructor
public class CustomResponse<T> {
    private boolean status;

    private String message;

    private int code;

    private T data;

    public CustomResponse(boolean status, String message, int code){
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public CustomResponse(boolean status, String message, int code, T data){
        this.status = status;
        this.message = message;
        this.code = code;
        this.data = data;
    }
}
