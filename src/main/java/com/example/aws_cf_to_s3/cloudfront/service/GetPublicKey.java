package com.example.aws_cf_to_s3.cloudfront.service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.cloudfront.CloudFrontClient;
import software.amazon.awssdk.services.cloudfront.model.ListPublicKeysResponse;
import software.amazon.awssdk.services.cloudfront.model.PublicKeySummary;

@Slf4j
public class GetPublicKey {

    private GetPublicKey() {
    }

    private static final String PUBLIC_KEY_NAME = "da_public_key";

    public static String getPublicKey(
        CloudFrontClient cloudFrontClient
    ) {
        ListPublicKeysResponse listPublicKeysResponse = cloudFrontClient.listPublicKeys();
        List<PublicKeySummary> items = listPublicKeysResponse.publicKeyList().items();
        PublicKeySummary daRsaKey = items.stream()
            .filter(item -> item.name().equals(PUBLIC_KEY_NAME))
            .findFirst()
            .orElse(
                PublicKeySummary.builder()
                    .id("")
                    .build()
            );

        return daRsaKey.id();
    }
}
