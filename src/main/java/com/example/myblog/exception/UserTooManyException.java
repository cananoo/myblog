package com.example.myblog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//自定义异常
@ResponseStatus(value = HttpStatus.NOT_FOUND)   //HttpStatus.NOT_FOUND 用来指定返回的状态码，这里是404，会跳转到404页面
public class UserTooManyException extends RuntimeException {
    public UserTooManyException() {
    }

    //message作为参数传给前端进行接收
    public UserTooManyException(String message) {
        super(message);
    }
}
