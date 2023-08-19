package com.example.myblog.aspect;


import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;



//用切面实现日志功能(记录访问的url,ip,调用的方法，参数及返回值)--打印控制台
@Aspect
@Component
public class LogAspect {
    private  final Logger logger = LoggerFactory.getLogger(this.getClass());

    //抽取公共的切入点表达式(拦截controller下的所有方法)
    @Pointcut("execution(* com.example.myblog.controller.*.*(..))")
    public void log(){

    }

    //在切入点之前执行
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
         //获取request
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString(); //获取url
        String ip = request.getRemoteAddr(); //获取ip
        String classMethod = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName(); //获取类名和方法名
        Object[] args = joinPoint.getArgs(); //获取参数
        RequestLog requestLog = new RequestLog(url,ip,classMethod,args);
        logger.info("Request : {}",requestLog);
    }

    //在切入点后执行
    @After("log()")
    public void doAfter(){
      //  logger.info("doAfter");
    }

    //获取返回值
    @AfterReturning(returning = "result" ,pointcut="log()") //result是返回值
    public void doAfterReturn(Object result){
        //最终返回的结果
        logger.info("Result : {}",result);

    }


    //定义一个内部类，用来封装请求的url,ip,调用的方法，参数
    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url,String ip,String classMethod,Object[] args){
            this.url=url;
            this.ip=ip;
            this.classMethod=classMethod;
            this.args=args;
        }

        @Override
        public String toString(){
            return String.format("Request : {url : %s, ip : %s, classMethod : %s, args : %s}",url,ip,classMethod,args);
        }
    }
}
