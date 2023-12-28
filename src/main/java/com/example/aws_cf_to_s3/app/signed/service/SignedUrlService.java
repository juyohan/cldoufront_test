package com.example.aws_cf_to_s3.app.signed.service;

import com.example.aws_cf_to_s3.app.signed.dto.SignedForm.Response;
import com.example.aws_cf_to_s3.cloudfront.service.SignedPolicyRequest;
import com.example.aws_cf_to_s3.cloudfront.util.SigningUtilities;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cloudfront.model.CannedSignerRequest;
import software.amazon.awssdk.services.cloudfront.model.CustomSignerRequest;
import software.amazon.awssdk.services.cloudfront.url.SignedUrl;


@Service
@RequiredArgsConstructor
public class SignedUrlService {
    private final SignedPolicyRequest signedPolicyRequest;

    public Response createCannedSignedUrl(
        String resourcePath
    ) throws Exception {
        CannedSignerRequest cannedPolicy = signedPolicyRequest.createRequestForCannedPolicy(convertSpaceBar(resourcePath));

        SignedUrl signedUrl = SigningUtilities.signUrlForCannedPolicy(cannedPolicy);
        return Response.builder().signedUrl(signedUrl.url()).build();
    }

    public Response createCustomSignedUrl(
        String resourcePath
    ) throws Exception {
        CustomSignerRequest customPolicy = signedPolicyRequest.createRequestForCustomPolicy(convertSpaceBar(resourcePath));

        SignedUrl signedUrl = SigningUtilities.signUrlForCustomPolicy(customPolicy);
        return Response.builder().signedUrl(signedUrl.url()).build();
    }


    private String convertSpaceBar(String resourcePath) {
        return resourcePath.replace(" ", "+");
    }

}
