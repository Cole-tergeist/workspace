package com.techelevator.service;

import com.techelevator.dao.ProfileDao;
import com.techelevator.model.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    private final ProfileDao profileDao;

    public ProfileService(ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    public Profile createProfile(Profile profile) {
        return profileDao.createProfile(profile);
    }

    public Profile updateProfile(Profile profile) {
        return profileDao.updateProfile(profile);
    }

    public Profile getProfileByUserId(int userId) {
        return profileDao.getProfileByUserId(userId);
    }

    public List<Profile> getAllProfiles() {
        return profileDao.getAllProfiles();
    }

    public boolean deleteProfileByUserId(int userId) {
        return profileDao.deleteProfileByUserId(userId);
    }

    public List<Profile> findNearbyProfessionals() {
        return profileDao.findNearbyProfessionals();
    }


}
