package com.example.aws_cf_to_s3.cloudfront.service;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.cloudfront.CloudFrontClient;

public class CreateKeyGroup {
    private CreateKeyGroup() {}
    private static final Logger logger = LoggerFactory.getLogger(CreateKeyGroup.class);

    public static String createKeyGroup(CloudFrontClient cloudFrontClient, String publicKeyId) {
        String keyGroupId = cloudFrontClient.createKeyGroup(b -> b.
                keyGroupConfig(c -> c
                    .items(publicKeyId)
                    .name("JavaKeyGroup"+ UUID.randomUUID())))
            .keyGroup().id();
        logger.info("KeyGroup created with ID: [{}]", keyGroupId);
        return keyGroupId;
    }
}
