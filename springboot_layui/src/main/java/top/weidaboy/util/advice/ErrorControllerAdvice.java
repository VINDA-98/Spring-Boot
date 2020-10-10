package top.weidaboy.util.advice;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import top.weidaboy.util.ResponseUtil;

/**
 * 全局异常处理增强类
 * 返回JSON
 */
@RestControllerAdvice
public class ErrorControllerAdvice {
    @ExceptionHandler(Exception.class)
    public Object handlerError(Exception ex, HandlerMethod handlerMethod) {
        String exMessage = ex.getMessage();
        String exMeethodName = handlerMethod.getMethod().getName();
        Class exClass = handlerMethod.getBean().getClass();
        String exClassName = exClass.getName();
        return ResponseUtil.general_response( ResponseUtil.CODE_EXCEPTION,
                "ExMessage: "+ exMessage + " ExMethod: " + exMeethodName + " ExClass: " + exClassName);
    }
}
