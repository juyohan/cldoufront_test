package com.example.aws_cf_to_s3.app.key.adaptor;

import com.example.aws_cf_to_s3.app.key.form.KeyForm;
import com.example.aws_cf_to_s3.app.key.service.KeyService;
import java.security.NoSuchAlgorithmException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KeyAdaptor {
    private final KeyService keyService;

    @Autowired
    public KeyAdaptor(KeyService keyService) {
        this.keyService = keyService;
    }

    public void updateKey() {
        String publicKeyId = keyService.getPublicKey();

        log.info("public key ID : {}", publicKeyId);

        if (!publicKeyId.equals("")) {
            keyService.removeKeyOfKeyGroup(publicKeyId);
            keyService.removePublicKey(publicKeyId);
        }

        try {
            KeyForm keyPair = keyService.createRsaKeyPair();
            String newPublicKey = keyService.createPublicKey(keyPair.getPublicKey());
            keyService.addKeyOfKeyGroup(newPublicKey);
        } catch (NoSuchAlgorithmException e) {
            log.error("키페어 생성 에러 메시지 : {}", e.getMessage());
        }
    }
}
