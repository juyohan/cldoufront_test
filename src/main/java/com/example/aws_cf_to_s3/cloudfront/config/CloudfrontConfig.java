package com.example.aws_cf_to_s3.cloudfront.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudfront.CloudFrontClient;

@Configuration
public class CloudfrontConfig {

    @Bean
    public CloudFrontClient cloudFrontClient() {
        try(ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create()) {
            return CloudFrontClient.builder().credentialsProvider(credentialsProvider)
                .region(Region.AWS_GLOBAL)
                .build();
        }
    }
}
