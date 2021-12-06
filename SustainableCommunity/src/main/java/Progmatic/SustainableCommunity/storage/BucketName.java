package Progmatic.SustainableCommunity.storage;

import lombok.Getter;

public enum BucketName {
    PROFILE_IMAGE("nemkacat-imgs");

    @Getter
    private final String BUCKET_NAME;

    BucketName(String BUCKET_NAME) {
        this.BUCKET_NAME = BUCKET_NAME;
    }
}
