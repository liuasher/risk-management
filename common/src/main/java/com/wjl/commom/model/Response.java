package com.wjl.commom.model;

import com.wjl.commom.enumeration.CodeEnum;
import lombok.Data;

@Data
public class Response<T> {
    private String code;
    private String message;
    private T data;

    public Response(CodeEnum code, T data) {
        this.code = code.getCode();
        this.message = code.getMessage();
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data.toString() +
                '}';
    }
}
