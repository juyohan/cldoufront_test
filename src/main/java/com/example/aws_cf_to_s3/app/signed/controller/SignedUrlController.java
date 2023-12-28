package com.example.aws_cf_to_s3.app.signed.controller;

import com.example.aws_cf_to_s3.app.signed.dto.SignedForm;
import com.example.aws_cf_to_s3.app.signed.dto.SignedForm.Response;
import com.example.aws_cf_to_s3.app.signed.service.SignedUrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/signed")
public class SignedUrlController {
    private final SignedUrlService signedUrlService;

    public SignedUrlController(SignedUrlService signedUrlService) {
        this.signedUrlService = signedUrlService;
    }

    @PostMapping("/canned")
    public Response getCannedSignedUrl(@RequestBody SignedForm.Request signedForm) throws Exception {
        return signedUrlService.createCannedSignedUrl(signedForm.getResourcePath());
    }

    @PostMapping("/custom")
    public Response getCustomSignedUrl(@RequestBody SignedForm.Request signedForm) throws Exception {
        return signedUrlService.createCustomSignedUrl(signedForm.getResourcePath());
    }
}
