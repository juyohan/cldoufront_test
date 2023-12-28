package com.example.aws_cf_to_s3.cloudfront.service;

import com.example.aws_cf_to_s3.cloudfront.dto.KeyGroupForm;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.cloudfront.CloudFrontClient;
import software.amazon.awssdk.services.cloudfront.model.GetKeyGroupRequest;
import software.amazon.awssdk.services.cloudfront.model.GetKeyGroupResponse;

@Slf4j
public class GetGroupKey {
    private GetGroupKey() {}

    public static final String KEY_GROUP_ID = "7c656b66-ad94-4bfb-80fa-1b6b57082985";

    public static KeyGroupForm getGroupKey(
        CloudFrontClient cloudFrontClient
    ) {
        GetKeyGroupRequest request = GetKeyGroupRequest.builder()
            .id(KEY_GROUP_ID).build();
        GetKeyGroupResponse keyGroup = cloudFrontClient.getKeyGroup(request);

        return KeyGroupForm.builder()
            .keyGroupConfig(keyGroup.keyGroup().keyGroupConfig())
            .etag(keyGroup.eTag())
            .build();
    }
}
