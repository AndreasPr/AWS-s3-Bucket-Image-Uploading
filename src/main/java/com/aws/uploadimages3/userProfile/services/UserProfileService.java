package com.aws.uploadimages3.userProfile.services;

import com.aws.uploadimages3.filestore.Filestore;
import com.aws.uploadimages3.s3bucket.NameOfBucket;
import com.aws.uploadimages3.userProfile.domain.UserProfile;
import com.aws.uploadimages3.userProfile.repositories.UserProfileRepository;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final Filestore filestore;

    @Autowired
    public UserProfileService(UserProfileRepository userProfileRepository, Filestore filestore) {
        this.userProfileRepository = userProfileRepository;
        this.filestore = filestore;
    }

    public List<UserProfile> getUserProfiles() {
        return userProfileRepository.getUserProfiles();
    }

    public void uploadImage(UUID profileId, MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new IllegalStateException("Empty file( " + multipartFile.getSize() + " ) cannot be uploaded!");
        }

        if (!Arrays.asList(ContentType.IMAGE_JPEG.getMimeType(), ContentType.IMAGE_PNG.getMimeType(), ContentType.IMAGE_GIF.getMimeType()).contains(multipartFile.getContentType())) {
            throw new IllegalStateException("File must be an Image!");
        }

        UserProfile userProfile = getUserProfileOrThrow(profileId);

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", multipartFile.getContentType());
        metadata.put("Content-Length", String.valueOf(multipartFile.getSize()));

        String path = String.format("%s/%s", NameOfBucket.PROFILE_IMAGE.getNameOfBucket(), userProfile.getProfileId());
        String filename = String.format("%s-%s", multipartFile.getOriginalFilename(), UUID.randomUUID());
        try {
            filestore.save(path, filename, Optional.of(metadata), multipartFile.getInputStream());
            userProfile.setImageLink(filename);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private UserProfile getUserProfileOrThrow(UUID profileId){
        return userProfileRepository.getUserProfiles()
                .stream()
                .filter(profile -> profile.getProfileId().equals(profileId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("User Profile with id: " + profileId + " not found!"));
    }

    public byte[] downloadImage(UUID profileId){
        UserProfile userProfile = getUserProfileOrThrow(profileId);
        String path = String.format("%s/%s", NameOfBucket.PROFILE_IMAGE.getNameOfBucket(), userProfile.getProfileId());
        return userProfile.getImageLink().map(key -> filestore.download(path, key)).orElse(new byte[0]);
    }

}
