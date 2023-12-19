package com.example.aws_cf_to_s3.cloudfront.policy;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.cloudfront.model.CannedSignerRequest;
import software.amazon.awssdk.services.cloudfront.model.CustomSignerRequest;

@Slf4j
@Component
public class SignPolicy {

    @Value("${aws.cloudfront.domain}")
    private String cloudfrontDomain;
    @Value("${aws.cloudfront.public-key}")
    private String publicKeyId;
    private final ClassPathResource resource = new ClassPathResource("da_private_key.der");

//    public SignPolicy(
//        @Value("${aws.cloudfront.public-key}") String publicKeyId,
//        @Value("${aws.cloudfront.domain}") String cloudfrontDomain
//    ) {
//        this.publicKeyId = publicKeyId;
//        this.cloudfrontDomain = cloudfrontDomain;
//    }

    public CannedSignerRequest createRequestForCannedPolicy(
        String resourcePath
    ) throws Exception {

        String protocol = "https";

        String cloudFrontUrl = new URL(protocol, cloudfrontDomain, resourcePath).toString();
        Instant expirationDate = Instant.now().plus(1, ChronoUnit.HOURS);
        Path path = Paths.get(resource.getPath());

        log.info("public key : {}", publicKeyId);

        return CannedSignerRequest.builder()
            .resourceUrl(cloudFrontUrl)
            .privateKey(path)
            .keyPairId(publicKeyId)
            .expirationDate(expirationDate)
            .build();
    }

    public CustomSignerRequest createRequestForCustomPolicy(
        String resourcePath
    ) throws Exception {

        String protocol = "https";

        String cloudFrontUrl = new URL(protocol, cloudfrontDomain, resourcePath).toString();
        Instant expireDate = Instant.now().plus(1, ChronoUnit.HOURS);
        // URL will be accessible tomorrow using the signed URL.
        Instant activeDate = Instant.now();
        Path path = Paths.get(resource.getPath());

        return CustomSignerRequest.builder()
            .resourceUrl(cloudFrontUrl)
            .privateKey(path)
            .keyPairId(publicKeyId)
            .expirationDate(expireDate)
            .activeDate(activeDate)      // Optional.
            //.ipRange("192.168.0.1/24") // Optional.
            .build();
    }

}