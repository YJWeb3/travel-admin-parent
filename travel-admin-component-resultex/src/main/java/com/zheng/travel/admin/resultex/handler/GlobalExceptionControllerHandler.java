package com.zheng.travel.admin.resultex.handler;

import com.zheng.travel.admin.commons.utils.json.JsonUtil;
import com.zheng.travel.admin.commons.enums.IResultEnum;
import com.zheng.travel.admin.commons.ex.TravelBussinessException;
import com.zheng.travel.admin.commons.ex.TravelValidationException;
import com.zheng.travel.admin.commons.resultex.ErrorHandler;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionControllerHandler {

    /**
     * 拦截所有程序异常
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ErrorHandler errorHandler(HttpServletRequest request, Exception ex) {
        ex.printStackTrace();
        return ErrorHandler.error(IResultEnum.SERVER_ERROR, ex.getMessage(), request.getRequestURL().toString());
    }

    /**
     * 对验证的统一异常进行统一处理
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorHandler handlerValiator(MethodArgumentNotValidException e) {
        e.printStackTrace();
        // 1: 获取验证框架中，所以不合法的属性异常信息
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        // 2: 把集合的异常信息，转换成Map返回。以属性名做为key,异常信息做为key
        Map<String, String> mapList = toValidatorMsg(fieldErrors);
        // 3: 返回错误信息
        ErrorHandler errorHandler = ErrorHandler.error(IResultEnum.SERVER_ERROR, JsonUtil.obj2String(mapList));
        return errorHandler;
    }

    private Map<String, String> toValidatorMsg(List<FieldError> fieldErrorList) {
        Map<String, String> map = new HashMap<>();
        for (FieldError fieldError : fieldErrorList) {
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return map;
    }

    /**
     * 对验证异常进行统一处理
     *
     * @param fieldErrorList
     * @return
     */
//    private List<Map<String, String>> toValidatorMsg(List<FieldError> fieldErrorList) {
//        List<Map<String, String>> mapList = new ArrayList<>();
//        for (FieldError fieldError : fieldErrorList) {
//            Map<String, String> map = new HashMap<>();
//            map.put("field", fieldError.getField());
//            map.put("msg", fieldError.getDefaultMessage());
//            mapList.add(map);
//        }
//        return mapList;
//    }



    /**
     * 拦截所有程序异常
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ErrorHandler errorHandler(HttpServletRequest request, RuntimeException ex) {
        ex.printStackTrace();
        return ErrorHandler.error(IResultEnum.SERVER_ERROR, ex.getMessage(), request.getRequestURL().toString());
    }


    /**
     * OrderException异常
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(value = TravelValidationException.class)
    @ResponseBody
    public ErrorHandler errorHandlerOrex(HttpServletRequest request, TravelValidationException ex) {
        ex.printStackTrace();
        return ErrorHandler.error(ex.getStatus(), ex.getMsg(), request.getRequestURL().toString());
    }

    /**
     * BussinessException异常
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(value = TravelBussinessException.class)
    @ResponseBody
    public ErrorHandler errorHandlerBex(HttpServletRequest request, TravelBussinessException ex) {
        ex.printStackTrace();
        return ErrorHandler.error(ex.getStatus(), ex.getMsg(), request.getRequestURL().toString());
    }


    /**
     * assert异常校验处理
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorHandler handlerIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        ex.printStackTrace();
        return ErrorHandler.error(701, ex.getMessage(), request.getRequestURL().toString());
    }
}
