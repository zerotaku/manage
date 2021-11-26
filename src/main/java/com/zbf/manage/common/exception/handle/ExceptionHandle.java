package com.zbf.manage.common.exception.handle;

import com.alibaba.fastjson.JSON;
import com.zbf.manage.common.Result;
import com.zbf.manage.common.exception.BusinessException;
import com.zbf.manage.common.util.RetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@ControllerAdvice
@RestControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Result<Object> bindExceptionErrorHandler(MethodArgumentNotValidException exception) {
        logger.error("bindExceptionErrorHandler info:{}", exception.getMessage());
        List<String> errorMsgList = new ArrayList<>();
        BindingResult bindingResult = exception.getBindingResult();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMsgList.add(fieldError.getDefaultMessage());
        }
        return RetResponse.error(JSON.toJSONString(errorMsgList));
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<Object> handle(Exception e) {
        if (e instanceof BusinessException) {
            BusinessException girlException = (BusinessException) e;
            return RetResponse.make(girlException.getCode(), girlException.getMessage());
        } else {
            logger.error("【系统异常】{}", e);
            return RetResponse.error("系统异常");
        }
    }

    @ExceptionHandler(BindException.class)
    public Result<Object> handleBindException(HttpServletRequest request, BindException exception) {
        List<FieldError> allErrors = exception.getFieldErrors();
        StringBuilder sb = new StringBuilder();
        for (FieldError errorMessage : allErrors) {
            sb.append(errorMessage.getDefaultMessage());
        }
        System.out.println(sb.toString());
        return RetResponse.error(sb.toString());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public Result<Object> handlerConstraintViolationException(ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        HashMap<String, String> objEroMsgMap = new HashMap<>();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            String heardMsg = constraintViolation.getLeafBean().toString();
            String message = constraintViolation.getMessage();
            if (objEroMsgMap.containsKey(heardMsg)) {
                String x = objEroMsgMap.get(heardMsg);
                objEroMsgMap.put(heardMsg, x + ";" + message);
            } else {
                objEroMsgMap.put(heardMsg, message);
            }
        }

        List<String> modelList = new ArrayList<>();
        for (String msg : objEroMsgMap.keySet()) {
            Matcher matcher = Pattern.compile("\\[(.*?)]").matcher(msg);
            while (matcher.find()) {
                modelList.add(matcher.group(1));
            }
            strBuilder.append(msg).append("{").append(objEroMsgMap.get(msg)).append("}").append("\n");
        }
        return RetResponse.error(strBuilder.toString(), modelList);
    }
}
