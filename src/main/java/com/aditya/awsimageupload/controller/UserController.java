package com.aditya.awsimageupload.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aditya.awsimageupload.model.UserProfile;
import com.aditya.awsimageupload.service.UserProfileService;

@RestController
@RequestMapping("/api/user-profile")
@CrossOrigin("*")
public class UserController {
    
    @Autowired
    private UserProfileService userService;

    @GetMapping
    public List<UserProfile> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping(
            path = "{profileId}/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void uploadProfileImage(@PathVariable UUID profileId,
                                    @RequestParam MultipartFile profileImage){
            userService.uploadProfileImage(profileId, profileImage);
    }
    @GetMapping("{profileId}/image/download")
    public byte[] downloadProfileimage(@PathVariable UUID profileId){
        return userService.downloadProfileImage(profileId);
    }
}
