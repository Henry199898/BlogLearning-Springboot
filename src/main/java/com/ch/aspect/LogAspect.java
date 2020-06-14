package com.ch.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/*前端日志返回，需要后端服务器记录的内容
 *  请求 url
 *  访问者 ip
 *  调用方法 classMethod
 *  参数 args
 *  返回内容*/


//定义一个切面，项目最开始时截取web项目下面的所有方法
@Aspect
@Component
public class LogAspect {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
//    定义一个切面，内部可以暂时不加内容，只作为一个切面标记
//                  任何方法  com。ch。web。中所有的类。所有的方法（所有的参数）
    @Pointcut("execution(* com.ch.web.*.*(..))")
    public void log(){}

//    在切面方法执行之前执行doBefore，在执行AOP切面的方法之前提前执行收取前端的log数据
    @Before("log()")
//    joinpoint就是那个截面，可以使用它获取到界面中方法的类名与方法名字。
    public void doBefore(JoinPoint joinPoint){
//       分别获取日志中需要的数据url，IP，等，request是用于获取ip和url数据
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        String url=request.getRequestURL().toString();
        String ip= request.getRemoteAddr();
        String classMethod=joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        Object[] args=joinPoint.getArgs();
        RequestLog requestLog=new RequestLog(url,ip,classMethod,args);
//        在控制器界面将获取到的相关数据都展示出来
        logger.info("Result:{}",requestLog);
    }

//   在切面方法执行之后执行doAfter
    @After("log()")
    public  void doAfter(){
//        logger.info(".........DOAfter.........");
    }

//  在所有的操作结束之后返回结果相关信息
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result){
        logger.info("Result:{}",result);
    }
//    获得日志各个参数
    public class RequestLog{

        private String url;
            private String ip;
            private String classMethod;
            private Object[] args;
    //        全参构造器
            public RequestLog(String url, String ip, String classMethod, Object[] args) {
                this.url = url;
                this.ip = ip;
                this.classMethod = classMethod;
                this.args = args;
            }
            @Override
            public String toString() {
                return "{" +
                        "url='" + url + '\'' +
                        ", ip='" + ip + '\'' +
                        ", classMethod='" + classMethod + '\'' +
                        ", args=" + Arrays.toString(args) +
                        '}';
            }

    }


}
