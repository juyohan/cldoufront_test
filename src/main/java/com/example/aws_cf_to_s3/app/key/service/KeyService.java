package com.example.aws_cf_to_s3.app.key.service;

import com.example.aws_cf_to_s3.app.key.form.KeyForm;
import com.example.aws_cf_to_s3.cloudfront.service.CreatePublicKey;
import com.example.aws_cf_to_s3.cloudfront.service.DeleteSigningResources;
import com.example.aws_cf_to_s3.cloudfront.service.GetPublicKey;
import com.example.aws_cf_to_s3.cloudfront.service.KeyGroupService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.openssl.jcajce.JcaPKCS8Generator;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemObjectGenerator;
import org.bouncycastle.util.io.pem.PemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cloudfront.CloudFrontClient;

@Service
@Slf4j
public class KeyService {

    CloudFrontClient cloudFrontClient;

    @Autowired
    public KeyService(CloudFrontClient cloudFrontClient) {
        this.cloudFrontClient = cloudFrontClient;
    }

    public KeyForm createRsaKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair pair = generator.generateKeyPair();

        KeyForm keyForm = new KeyForm();

        StringWriter sw = new StringWriter();
        try (JcaPEMWriter writer = new JcaPEMWriter(sw)) {
            PemObjectGenerator privateKey = new JcaPKCS8Generator(pair.getPrivate(), null);
            writer.writeObject(privateKey);
        } catch (IOException e) {
            log.error("error : {}", e.getMessage());
        }

        keyForm.setPrivateKey(sw.toString());
        sw = new StringWriter();

        try (PemWriter pemWriter = new PemWriter(sw)) {
            pemWriter.writeObject(new PemObject("PUBLIC KEY", pair.getPublic().getEncoded()));
            pemWriter.flush();
        } catch (IOException e) {
            log.error("error : {}", e.getMessage());
        }

        keyForm.setPublicKey(sw.toString());

        savePrivateKey(keyForm.getPrivateKey());

        return keyForm;
    }

    private void savePrivateKey(String privateKey) {
        File file = new File(System.getProperty("user.home") + "/key/da_private_key.pem");
        if (isKeyFolder()) {
            try (FileOutputStream stream = new FileOutputStream(file)) {
                stream.write(privateKey.getBytes());
                stream.flush();
            } catch (IOException e) {
                log.error("message : {}", e.getMessage());
            }
        }
    }

    private boolean isKeyFolder() {
        File folder = new File(System.getProperty("user.home") + "/key");
        if (!folder.exists()) {
            return folder.mkdir();
        }
        return true;
    }

    public String getPublicKey() {
        return GetPublicKey.getPublicKey(cloudFrontClient);
    }

    public void removeKeyOfKeyGroup(
        String publicKeyId
    ) {
        if (KeyGroupService.isKeyOfKeyGroup(cloudFrontClient, publicKeyId)) {
            KeyGroupService.removeKeyOfKeyGroup(cloudFrontClient, publicKeyId);
        }
    }

    public void removePublicKey(String publicKeyId) {
        DeleteSigningResources.deletePublicKey(cloudFrontClient, publicKeyId);
    }

    public String createPublicKey(String publicKeyId) {
        return CreatePublicKey.createPublicKey(cloudFrontClient, publicKeyId);
    }

    public void addKeyOfKeyGroup(String publicKeyId) {
        KeyGroupService.addKeyOfKeyGroup(cloudFrontClient, publicKeyId);
    }
}
