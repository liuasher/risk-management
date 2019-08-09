package com.wjl.commom.exception;

import com.wjl.commom.enumeration.CodeEnum;
import lombok.Data;

@Data
public class BaseException extends RuntimeException {
    private CodeEnum codeEnum;

    public BaseException(CodeEnum codeEnum) {
        super(codeEnum.getMessage());
        this.codeEnum = codeEnum;
    }
}
