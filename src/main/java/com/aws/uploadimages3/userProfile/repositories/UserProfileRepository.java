package com.aws.uploadimages3.userProfile.repositories;

import com.aws.uploadimages3.userProfile.domain.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserProfileRepository {

    private final DatabaseOfSampleData databaseOfSampleData;

    @Autowired
    public UserProfileRepository(DatabaseOfSampleData databaseOfSampleData) {
        this.databaseOfSampleData = databaseOfSampleData;
    }

    public List<UserProfile> getUserProfiles(){
        return databaseOfSampleData.getUserProfiles();
    }
}
