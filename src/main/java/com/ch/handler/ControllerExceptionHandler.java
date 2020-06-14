//package com.ch.handler;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.annotation.AnnotationUtils;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//
////返回到自己定义的错误页面
////异常测试抓取异常情况，并返回自定义的相关异常页面
//@ControllerAdvice//拦截所有标注有Controller注解的控制器
//public class ControllerExceptionHandler {
////    加载log信息的工具Logger
//    private final Logger logger=LoggerFactory.getLogger(this.getClass());
//    @ExceptionHandler(Exception.class)
//    public ModelAndView exceptionHander(HttpServletRequest request,Exception e) throws Exception {
//        //拦截了所有异常
//        logger.error("request URL:{},Exception:{}",request.getRequestURL());
////        做一个判断是否存在另一个异常状态信息，若有则返回到它该存在的异常信息页面中
//        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null){
//            throw e;
//        }
////        猜测应该时MVC的内容？
//        ModelAndView mv=new ModelAndView();
//        mv.addObject("url",request.getRequestURL());
//        mv.addObject("exception",e);
//        mv.setViewName("error/error");
//        return mv;
//    }
//
//}
//
