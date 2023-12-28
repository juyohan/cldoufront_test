package com.example.aws_cf_to_s3.common.error;


import org.springframework.http.HttpStatus;

public class DataNotFoundException extends RuntimeException {
    private ExceptionCode code;
    private String message;
    public HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public DataNotFoundException(String message) {
        super();
        this.message = message;
    }

    public DataNotFoundException(ExceptionCode code) {
        super();
        this.code = code;
    }

    public DataNotFoundException(ExceptionCode code, String message) {
        super();
        this.code = code;
        this.message = message;
    }
}
