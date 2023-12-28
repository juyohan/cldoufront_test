package com.example.aws_cf_to_s3.app.key.form;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KeyForm {
    private String privateKey;
    private String publicKey;
}
