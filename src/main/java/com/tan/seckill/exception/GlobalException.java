package com.tan.seckill.exception;

import com.tan.seckill.result.CodeMsg;

/**
 * @ClassName GlobalException
 * @Description 自定义全局异常类
 * @Author tan
 * @Date 2020/4/15 13:35
 * @Version 1.0
 **/
public class GlobalException extends RuntimeException {

    private static final long servialVersionUID = 1L;

    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg) {
        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }
}
