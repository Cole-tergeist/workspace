package com.techelevator.controller;

import com.techelevator.dao.ProfileDao;
import com.techelevator.model.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/profiles")
@CrossOrigin
public class ProfileController {

    private final ProfileDao profileDao;

    public ProfileController(ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    @GetMapping("/{userId}")
    @PreAuthorize("isAuthenticated()")
    public Profile getProfile(@PathVariable int userId) {
        Profile profile = profileDao.getProfileByUserId(userId);
        if (profile == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found");
        }
        return profile;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public Profile createOrUpdateProfile(@RequestBody Profile profile) {
        Profile existing = profileDao.getProfileByUserId(profile.getUserId());
        if (existing == null) {
            return profileDao.createProfile(profile);
        } else {
            return profileDao.updateProfile(profile);
        }
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@RequestParam int userId) {
        // Assumes profileDao.deleteProfileByUserId returns boolean success.
        boolean deleted = profileDao.deleteProfileByUserId(userId);
        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found");
        }
    }

    @PutMapping
    @PreAuthorize("isAuthenticated()")
    public Profile updateProfile(@RequestBody Profile profile) {
        Profile updated = profileDao.updateProfile(profile);
        if (updated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found");
        }
        return updated;
    }

    @GetMapping("/pregnant")
    @CrossOrigin(origins = "*")
    public List<Profile> getPregnantUsers() {
        return profileDao.getProfilesByRole("ROLE_USER");
    }

}