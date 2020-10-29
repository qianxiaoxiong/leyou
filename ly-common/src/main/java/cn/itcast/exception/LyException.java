package cn.itcast.exception;

import lombok.Getter;

@Getter
public class LyException extends  RuntimeException {

    private    ExceptionEnum exceptionEnum;

    public LyException(ExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;

    }
}
