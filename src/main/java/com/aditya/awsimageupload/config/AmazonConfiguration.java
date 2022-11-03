package com.aditya.awsimageupload.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.regions.Regions;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AmazonConfiguration {


    @Bean
    public AmazonS3 s3Client(){
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(
            "ACCESS_KEY",
            "SECRET_kEY"
            );
        return AmazonS3ClientBuilder
                    .standard()
                    .withRegion(Regions.US_WEST_2)
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .build();
                }
    
}
