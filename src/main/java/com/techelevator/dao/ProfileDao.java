package com.techelevator.dao;

import com.techelevator.model.Profile;
import java.util.List;

public interface ProfileDao {

    Profile createProfile(Profile profile);
    Profile updateProfile(Profile profile);
    Profile saveProfile(Profile profile);
    Profile getProfileByUserId(int userId);
    List<Profile> getAllProfiles();
    boolean deleteProfileByUserId(int userId);
    List<Profile> findNearbyProfessionals();
    // added for mod3mid
    List<Profile> getProfilesByRole(String role);

}