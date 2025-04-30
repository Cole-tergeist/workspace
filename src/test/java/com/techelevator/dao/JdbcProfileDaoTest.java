package com.techelevator.dao;

import com.techelevator.model.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcProfileDaoTest extends BaseDaoTest {
    protected static final Profile PROFILE_1 = new Profile(1, "Expecting first child",
            "Denver, CO", "5 years", "Midwife", "Natural Birth", 39.7392, -104.9903);

    protected static final Profile PROFILE_2 = new Profile(2, "Looking for midwife",
            "Boulder, CO", "10 years", "Doula", "Home Birth", 40.0150, -105.2705);

    private JdbcProfileDao dao;

    @BeforeEach
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        dao = new JdbcProfileDao(jdbcTemplate);
    }

    @Test
    public void getProfileByUserId_invalidId_returnsNull() {
        Profile profile = dao.getProfileByUserId(-1);
        assertNull(profile, "Expected null for invalid user ID");
    }

    @Test
    public void createProfile_validData_createsProfile() {
        Profile newProfile = new Profile(6, "Test bio", "Denver, CO", "5 years",
                "Midwife", "Natural Birth", 39.7392, -104.9903);
        Profile createdProfile = dao.saveProfile(newProfile);

        assertNotNull(createdProfile, "Expected non-null profile after creation");
        assertEquals("Denver, CO", createdProfile.getLocation(), "Expected location to be Denver, CO");
    }

    @Test
    public void getAllProfiles_returnsProfiles() {
        List<Profile> profiles = dao.getAllProfiles();

        assertNotNull(profiles, "Expected non-null list of profiles");
        assertFalse(profiles.isEmpty(), "Expected at least one profile");
    }

    @Test
    public void deleteProfile_validId_deletesSuccessfully() {
        int userId = 1;
        assertTrue(dao.deleteProfileByUserId(userId), "Expected delete to return true");
        assertNull(dao.getProfileByUserId(userId), "Deleted profile should not exist");
    }
}
