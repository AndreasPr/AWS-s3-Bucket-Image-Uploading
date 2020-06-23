package com.aws.uploadimages3.s3bucket;

public enum NameOfBucket {

    PROFILE_IMAGE("Here the name of the bucket that we create in s3 AWS");
    private final String nameOfBucket;

    NameOfBucket(String nameOfBucket) {
        this.nameOfBucket = nameOfBucket;
    }

    public String getNameOfBucket() {
        return nameOfBucket;
    }
}
