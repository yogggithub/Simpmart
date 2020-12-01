package com.simpmart.commodity.exception.controlleradvice;

import com.simpmart.common.exception.ErrCodeEnum;
import com.simpmart.common.utils.R;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Unified Controller Exception Handling
 */
@RestControllerAdvice(basePackages = "com.simpmart.commodity.controller")
public class MyControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R validationExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> error = new HashMap<>();
        BindingResult bindingResult = e.getBindingResult();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            error.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return R.error(ErrCodeEnum.VALID_EXCEPTION.getCode(),
                        ErrCodeEnum.VALID_EXCEPTION.getErrMsg())
                // why is response in code 200
                .put("error_info", error);
    }

    @ExceptionHandler(value = Throwable.class)
    public R handlerAllOther(Throwable throwable) {
        System.out.println(throwable.getMessage());
        return R.error(ErrCodeEnum.UNKNOWN_EXCEPTION.getCode(),
                        ErrCodeEnum.UNKNOWN_EXCEPTION.getErrMsg());
    }
}
