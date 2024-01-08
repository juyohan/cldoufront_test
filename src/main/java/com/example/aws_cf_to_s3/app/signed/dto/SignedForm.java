package com.example.aws_cf_to_s3.app.signed.dto;

import lombok.Builder;
import lombok.Data;

public class SignedForm {
    private SignedForm() {}

    @Data
    public static class Request {
        private String resourcePath;
    }

    @Data
    @Builder(toBuilder = true)
    public static class Response {
        private String signedUrl;
    }
}
