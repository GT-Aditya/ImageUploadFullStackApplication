package com.aditya.awsimageupload.service_impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aditya.awsimageupload.buckets.Buckets;
import com.aditya.awsimageupload.dao.MongoRepo;
import com.aditya.awsimageupload.file_upload_handler.FileUploadHandling;
import com.aditya.awsimageupload.model.UserProfile;
import com.aditya.awsimageupload.service.UserProfileService;


@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private MongoRepo userRepository;
    @Autowired
    private FileUploadHandling fileUploadSevice;


    @Override
    public List<UserProfile> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void uploadProfileImage(UUID profileId, MultipartFile profileImage) {
       if(profileImage.isEmpty())
            throw new IllegalArgumentException("Cannot upload empty file [ " + profileImage.getSize() + " ] ");
        if(
            !Arrays.asList(ContentType.IMAGE_JPEG.getMimeType(),
                        ContentType.IMAGE_PNG.getMimeType(),
                        ContentType.IMAGE_GIF.getMimeType())
                        .contains(profileImage.getContentType())
        ){
            throw new IllegalArgumentException("File must be an image !!");
        }

        UserProfile user = getUserProfileOrElse(profileId);
        
        //for metadata
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", profileImage.getContentType());
        metadata.put("Content-Length", String.valueOf(profileImage.getSize()));

        // creating path of file
        String path = String.format("%s/%s", Buckets.PROFILE_IMAGE.getBucketName(), user.getProfileId());
        String filename = String.format("%s-%s", profileImage.getOriginalFilename(), UUID.randomUUID());

        try{
            fileUploadSevice.save(path, filename, Optional.of(metadata), profileImage.getInputStream());
            user.setProfileImageLink(filename);
        }catch(IOException ex){
            throw new IllegalStateException(ex);
        }
    }

    private UserProfile getUserProfileOrElse(UUID profileId) {
       return userRepository.findById(profileId).orElseThrow(()->
                                                             new IllegalStateException(
                                                                    String.format("User Profile %s is not found",
                                                                                 profileId)));
    }

    @Override
    public byte[] downloadProfileImage(UUID profileId) {
        UserProfile user = getUserProfileOrElse(profileId);
        String path = String.format("%s/%s", Buckets.PROFILE_IMAGE.getBucketName(), user.getProfileId());
        return user.getProfileImageLink().map(key->fileUploadSevice.download(path, key)).orElse(new byte[0]);
    }
    
}
