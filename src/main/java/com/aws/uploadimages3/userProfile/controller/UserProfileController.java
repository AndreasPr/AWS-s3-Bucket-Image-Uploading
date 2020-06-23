package com.aws.uploadimages3.userProfile.controller;

import com.aws.uploadimages3.userProfile.domain.UserProfile;
import com.aws.uploadimages3.userProfile.services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("api/user-profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public List<UserProfile> getUserProfiles(){
        return userProfileService.getUserProfiles();
    }

    @GetMapping("{profileId}/image/download")
    public byte[] downloadProfileImage(@PathVariable("profileId") UUID profileId){
        return userProfileService.downloadImage(profileId);
    }

    @PostMapping(path = "{profileId}/image/upload", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadProfileImage(@PathVariable("profileId") UUID profileId, @RequestParam("file") MultipartFile file){
        userProfileService.uploadImage(profileId, file);
    }
}
