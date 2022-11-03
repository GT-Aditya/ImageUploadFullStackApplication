package com.aditya.awsimageupload.service;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.aditya.awsimageupload.model.UserProfile;

public interface UserProfileService {

    List<UserProfile> getAllUsers();

    void uploadProfileImage(UUID profileId, MultipartFile profileImage);

    byte[] downloadProfileImage(UUID profileId);
    
}
