package com.tan.seckill.exception;

import com.tan.seckill.result.CodeMsg;
import com.tan.seckill.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName GlobalExceptionHandler
 * @Description 自定义全局异常拦截器
 * @Author tan
 * @Date 2020/4/21 22:45
 * @Version 1.0
 **/
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler extends RuntimeException {

    @ExceptionHandler(value = Exception.class)//拦截所有异常
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        if (e instanceof GlobalException) {
            GlobalException ex = (GlobalException) e;
            return Result.error(ex.getCodeMsg());
        } else if (e instanceof BindException) {
            BindException ex = (BindException) e;
            List<ObjectError> errors = ex.getAllErrors();//绑定错误返回很多错误，是一个错误列表，只需要第一个错误
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));//给状态码填充参数
        } else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}
