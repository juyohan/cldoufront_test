package com.example.aws_cf_to_s3.cloudfront.service;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.cloudfront.CloudFrontClient;
import software.amazon.awssdk.services.cloudfront.model.CreatePublicKeyResponse;

@Component
public class CreatePublicKey {
    private CreatePublicKey() {}

    private static final Logger logger = LoggerFactory.getLogger(CreatePublicKey.class);

    public static String createPublicKey(
        CloudFrontClient cloudFrontClient,
        String publicKey
    ) {
        CreatePublicKeyResponse createPublicKeyResponse = cloudFrontClient.createPublicKey(b -> b.
            publicKeyConfig(c -> c
                .name("da_public_key")
                .encodedKey(publicKey)
                .callerReference(UUID.randomUUID().toString())));
        String createdPublicKeyId = createPublicKeyResponse.publicKey().id();
        logger.info("Public key created with id: [{}]", createdPublicKeyId);

        return createdPublicKeyId;
    }
}
