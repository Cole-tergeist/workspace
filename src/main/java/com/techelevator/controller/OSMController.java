package com.techelevator.controller;

import com.techelevator.model.Profile;
import com.techelevator.service.OSMService;
import com.techelevator.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/location")
@CrossOrigin
public class OSMController {

    private final ProfileService profileService;
    private final OSMService osmService;

    public OSMController(ProfileService profileService, OSMService osmService) {
        this.profileService = profileService;
        this.osmService = osmService;
    }


    @GetMapping("/near_by_professionals")
    public List<Profile> getNearbyProfessionals() {
        return profileService.findNearbyProfessionals();
    }


    @PostMapping("/save_location")
    public Profile saveUserLocation(@RequestParam int userId, @RequestParam double lat, @RequestParam double lon) {
        Profile profile = profileService.getProfileByUserId(userId);
        if (profile == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found for user ID: " + userId);
        }
        profile.setLatitude(lat);
        profile.setLongitude(lon);
        return profileService.updateProfile(profile);
    }
}
