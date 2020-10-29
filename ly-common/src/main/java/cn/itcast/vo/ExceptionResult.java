package cn.itcast.vo;

import cn.itcast.exception.ExceptionEnum;
import lombok.Data;

@Data
public class ExceptionResult {

    private   Integer  status;
    private   String msg;
    private   Long  timstamp;

    public ExceptionResult(ExceptionEnum exceptionEnum) {
        this.status = exceptionEnum.getValue();
        this.msg = exceptionEnum.getMsg();
        this.timstamp =  System.currentTimeMillis();
    }
}
