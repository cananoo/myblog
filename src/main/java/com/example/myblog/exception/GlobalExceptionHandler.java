package com.example.myblog.exception;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

//全局异常处理类
@ControllerAdvice
public class GlobalExceptionHandler {
    //错误日志记录
    private  final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler
    public ModelAndView handleException(HttpServletRequest request, Exception e) throws Exception {
        //记录异常的url和异常信息
        logger.error("Request URL : {}, Exception : {}", request.getRequestURL(), e);
        //如果是自定义异常，就不会跳转到error页面，而是跳转到自定义的页面
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            throw e;
        }
        ModelAndView mv = new ModelAndView();
        //将异常信息和url传递到前端
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        //跳转到error页面
        mv.setViewName("error/error");
        return mv;
    }

}
