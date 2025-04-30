package com.techelevator.model;

public class Profile {
    private int profileId;
    private String name;
    private int userId;
    private String bio;
    private String location;
    private String experience;         // For professionals
    private String specialties;        // For professionals
    private String birthPreferences;   // For pregnant users
    private Double latitude;
    private Double longitude;

    public Profile() {}

    public Profile(int userId, String bio, String location, String experience,
                   String specialties, String birthPreferences, double latitude, double longitude) {
        this.userId = userId;
        this.bio = bio;
        this.location = location;
        this.experience = experience;
        this.specialties = specialties;
        this.birthPreferences = birthPreferences;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and setters
    public int getProfileId() {
        return profileId;
    }
    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getExperience() {
        return experience;
    }
    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSpecialties() {
        return specialties;
    }
    public void setSpecialties(String specialties) {
        this.specialties = specialties;
    }

    public String getBirthPreferences() {
        return birthPreferences;
    }
    public void setBirthPreferences(String birthPreferences) {
        this.birthPreferences = birthPreferences;
    }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    @Override
    public String toString() {
        return "ProfileDto{" +
                "profileId=" + profileId +
                ", userId=" + userId +
                ", bio='" + bio + '\'' +
                ", location='" + location + '\'' +
                ", experience='" + experience + '\'' +
                ", specialties='" + specialties + '\'' +
                ", birthPreferences='" + birthPreferences + '\'' +
                '}';
    }


}
