package com.product.manager.aspect;

import com.product.manager.exception.RateExternalServiceNoContentException;
import feign.FeignException;
import feign.RetryableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler({SocketTimeoutException.class, UnknownHostException.class, RetryableException.class})
    public ResponseEntity<?> handleTimeout(){
        return new ResponseEntity<>(HttpStatus.REQUEST_TIMEOUT);
    }
    @ExceptionHandler({IllegalArgumentException.class, FeignException.class})
    public ResponseEntity<?> handleBadRequest(){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({RateExternalServiceNoContentException.class})
    public ResponseEntity<?> handleRateService(){
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
