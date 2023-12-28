package com.example.aws_cf_to_s3.cloudfront.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.cloudfront.CloudFrontClient;
import software.amazon.awssdk.services.cloudfront.model.DeleteKeyGroupResponse;
import software.amazon.awssdk.services.cloudfront.model.DeletePublicKeyResponse;
import software.amazon.awssdk.services.cloudfront.model.GetKeyGroupResponse;
import software.amazon.awssdk.services.cloudfront.model.GetPublicKeyResponse;

public class DeleteSigningResources {
    private DeleteSigningResources() {}
    private static final Logger logger = LoggerFactory.getLogger(DeleteSigningResources.class);

    public static void deleteKeyGroup(final CloudFrontClient cloudFrontClient, final String keyGroupId){

        GetKeyGroupResponse getResponse = cloudFrontClient.getKeyGroup(b -> b.id(keyGroupId));
        DeleteKeyGroupResponse deleteResponse = cloudFrontClient.deleteKeyGroup(builder -> builder
            .id(keyGroupId)
            .ifMatch(getResponse.eTag()));
        if ( deleteResponse.sdkHttpResponse().isSuccessful() ){
            logger.info("Successfully deleted Key Group [{}]", keyGroupId);
        }
    }

    public static void deletePublicKey(final CloudFrontClient cloudFrontClient, final String publicKeyId){
        GetPublicKeyResponse getResponse = cloudFrontClient.getPublicKey(b -> b.id(publicKeyId));

        DeletePublicKeyResponse deleteResponse = cloudFrontClient.deletePublicKey(builder -> builder
            .id(publicKeyId)
            .ifMatch(getResponse.eTag()));

        if (deleteResponse.sdkHttpResponse().isSuccessful()){
            logger.info("Successfully deleted Public Key [{}]", publicKeyId);
        }
    }
}
