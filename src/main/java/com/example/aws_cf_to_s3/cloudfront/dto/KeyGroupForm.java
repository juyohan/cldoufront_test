package com.example.aws_cf_to_s3.cloudfront.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import software.amazon.awssdk.services.cloudfront.model.KeyGroupConfig;

@Data
@Builder
public class KeyGroupForm {

    private String etag;
    private KeyGroupConfig keyGroupConfig;

    public void setKeyItem(String key) {
        List<String> items = new ArrayList<>(keyGroupConfig.items());
        items.add(key);

        this.keyGroupConfig = KeyGroupConfig.builder()
            .name(keyGroupConfig.name())
            .items(items)
            .comment(keyGroupConfig.comment())
            .build();
    }

    public boolean removeKeyItem(String key) {
        List<String> items = new ArrayList<>(keyGroupConfig.items());

        if (items.remove(key)) {
            this.keyGroupConfig = KeyGroupConfig.builder()
                .name(keyGroupConfig.name())
                .items(items)
                .comment(keyGroupConfig.comment())
                .build();
            return true;
        }
        return false;
    }
}
