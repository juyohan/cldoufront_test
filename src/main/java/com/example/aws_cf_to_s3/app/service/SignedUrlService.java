package com.example.aws_cf_to_s3.app.service;

import com.example.aws_cf_to_s3.app.dto.SignedForm.Response;
import com.example.aws_cf_to_s3.cloudfront.policy.SignPolicy;
import com.example.aws_cf_to_s3.cloudfront.util.SigningUtilities;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cloudfront.model.CannedSignerRequest;
import software.amazon.awssdk.services.cloudfront.model.CustomSignerRequest;
import software.amazon.awssdk.services.cloudfront.url.SignedUrl;


@Service
@RequiredArgsConstructor
public class SignedUrlService {
    private final SignPolicy signPolicy;

    public Response createCannedSignedUrl(
        String resourcePath
    ) throws Exception {
        CannedSignerRequest cannedPolicy = signPolicy.createRequestForCannedPolicy(convertSpaceBar(resourcePath));

        SignedUrl signedUrl = SigningUtilities.signUrlForCannedPolicy(cannedPolicy);
        return Response.builder().signedUrl(signedUrl.url()).build();
    }

    public Response createCustomSignedUrl(
        String resourcePath
    ) throws Exception {
        CustomSignerRequest customPolicy = signPolicy.createRequestForCustomPolicy(convertSpaceBar(resourcePath));

        SignedUrl signedUrl = SigningUtilities.signUrlForCustomPolicy(customPolicy);
        return Response.builder().signedUrl(signedUrl.url()).build();
    }


    private String convertSpaceBar(String resourcePath) {
        return resourcePath.replace(" ", "+");
    }

}
