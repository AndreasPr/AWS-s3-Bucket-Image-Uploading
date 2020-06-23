package com.aws.uploadimages3.userProfile.domain;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class UserProfile {

    private UUID profileId;
    private String username;
    private String imageLink;

    public UserProfile(UUID profileId, String username, String imageLink) {
        this.profileId = profileId;
        this.username = username;
        this.imageLink = imageLink;
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

    public Optional<String> getImageLink() {
        return Optional.ofNullable(imageLink);
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserProfile)) return false;
        UserProfile that = (UserProfile) o;
        return  Objects.equals(profileId, that.profileId) &&
                Objects.equals(username, that.username) &&
                Objects.equals(imageLink, that.imageLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileId, username, imageLink);
    }
}
