package com.aditya.awsimageupload.file_upload_handler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;

@Service
public class FileUploadHandling {

    @Autowired
    private AmazonS3 s3;

    public void save(String path, String fileName,
            Optional<Map<String, String>> optionalMetaData,
            InputStream inputStream) {

        ObjectMetadata metadata = new ObjectMetadata();
        optionalMetaData.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach(metadata::addUserMetadata);
            }
        });
        try {
            s3.putObject(path, fileName, inputStream, metadata);
        } catch (AmazonServiceException ex) {
            throw new IllegalStateException("Failed to upload the file to s3: ", ex);
        }
    }

    public byte[] download(String path, String key) {
        try(S3Object object = s3.getObject(path, key)){
            return IOUtils.toByteArray(object.getObjectContent());
        }catch(SdkClientException | IOException e){
            throw new IllegalStateException("Failed to download file from s3", e);
        }
    }
}
