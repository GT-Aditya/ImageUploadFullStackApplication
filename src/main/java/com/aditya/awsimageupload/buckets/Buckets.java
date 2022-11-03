package com.aditya.awsimageupload.buckets;

public enum Buckets {
    PROFILE_IMAGE("youtube-tut-563fc");
    private String bucketName;
    Buckets(String bucketName){
        this.bucketName = bucketName;
    }
    public String getBucketName(){
        return bucketName;
    }
}
