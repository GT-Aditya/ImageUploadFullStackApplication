package com.aditya.awsimageupload.dao;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.aditya.awsimageupload.model.UserProfile;

@Repository
public interface MongoRepo extends MongoRepository<UserProfile, UUID>{
    
    
}
