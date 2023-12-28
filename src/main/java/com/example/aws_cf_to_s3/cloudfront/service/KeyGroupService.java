package com.example.aws_cf_to_s3.cloudfront.service;

import com.example.aws_cf_to_s3.cloudfront.dto.KeyGroupForm;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.cloudfront.CloudFrontClient;
import software.amazon.awssdk.services.cloudfront.model.UpdateKeyGroupRequest;

@Slf4j
public class KeyGroupService {

    private KeyGroupService() {
    }

    public static boolean isKeyOfKeyGroup(
        CloudFrontClient cloudFrontClient,
        String publicKeyId
    ) {
        return GetGroupKey.getGroupKey(cloudFrontClient).getKeyGroupConfig().items().stream()
            .anyMatch(item -> item.equals(publicKeyId));
    }

    public static void removeKeyOfKeyGroup(
        CloudFrontClient cloudFrontClient,
        String publicKeyId
    ) {
        KeyGroupForm keyGroupForm = GetGroupKey.getGroupKey(cloudFrontClient);

        if (keyGroupForm.removeKeyItem(publicKeyId)) {
            updateKeyGroup(cloudFrontClient, keyGroupForm);
        }
    }

    public static void addKeyOfKeyGroup(
        CloudFrontClient cloudFrontClient,
        String publicKeyId
    ) {
        KeyGroupForm keyGroupForm = GetGroupKey.getGroupKey(cloudFrontClient);
        keyGroupForm.setKeyItem(publicKeyId);

        updateKeyGroup(cloudFrontClient, keyGroupForm);
    }

    private static void updateKeyGroup(
        CloudFrontClient cloudFrontClient,
        KeyGroupForm keyGroupForm
    ) {
        UpdateKeyGroupRequest request = UpdateKeyGroupRequest.builder().id(GetGroupKey.KEY_GROUP_ID).ifMatch(keyGroupForm.getEtag())
            .keyGroupConfig(keyGroupForm.getKeyGroupConfig()).build();

        cloudFrontClient.updateKeyGroup(request);
    }

}
