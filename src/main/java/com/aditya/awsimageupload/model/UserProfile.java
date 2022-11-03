package com.aditya.awsimageupload.model;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_profiles")
public class UserProfile {

    private UUID profileId;
    private String username;
    private String profileImageLink; // for s3 file url

    public UserProfile() {
    }

    public UserProfile(UUID profileId, String username, String profileImageLink) {
        this.profileId = profileId;
        this.username = username;
        this.profileImageLink = profileImageLink;
    }

    public UUID getProfileId() {
        return profileId;
    }

    public void setProfileId(UUID profileId) {
        this.profileId = profileId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Optional<String> getProfileImageLink() {
        return Optional.ofNullable(profileImageLink);
    }

    public void setProfileImageLink(String profileImageLink) {
        this.profileImageLink = profileImageLink;
    }

}
