package com.example.aws_cf_to_s3.app.key.controller;

import com.example.aws_cf_to_s3.app.key.adaptor.KeyAdaptor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/key")
@RequiredArgsConstructor
public class KeyController {
    private final KeyAdaptor keyAdaptor;

    @PostMapping("/create")
    public void createRsaKey() {
        keyAdaptor.updateKey();
    }
}
