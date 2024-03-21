package com.trxyzng.trung.search.user_profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {
    @Autowired
    UserProfileRepo userProfileRepo;

    public UserProfileEntity[] findUserProfileEntitiesByName(String name, int number) {
        name = name.toUpperCase();
//        return userProfileRepo.findByNameStartingWith(name).orElse(new UserProfileEntity[]{});
        return userProfileRepo.findUserProfileEntitiesByName(name, number).orElse(new UserProfileEntity[]{});
    }
}
