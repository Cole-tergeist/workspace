package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcProfileDao implements ProfileDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcProfileDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Profile getProfileByUserId(int userId) {
        String sql = "SELECT * FROM profiles WHERE user_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            if (results.next()) {
                return mapRowToProfile(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return null;
    }

    @Override
    public List<Profile> getProfilesByRole(String role) {
        List<Profile> profiles = new ArrayList<>();
        String sql = "SELECT p.*, u.name " +
                "FROM profiles p " +
                "JOIN users u ON u.user_id = p.user_id " +
                "WHERE u.role = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, role);
        while (results.next()) {
            profiles.add(mapRowToProfile(results));
        }
        return profiles;
    }


    @Override
    public List<Profile> getAllProfiles() {
        List<Profile> profiles = new ArrayList<>();
        String sql = "SELECT * FROM profiles";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                profiles.add(mapRowToProfile(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return profiles;
    }

    @Override
    public Profile createProfile(Profile profile) {
        String sql = "INSERT INTO profiles (user_id, bio, location, experience, specialties, birth_preferences, latitude, longitude) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING profile_id";
        try {
            Integer profileId = jdbcTemplate.queryForObject(sql, Integer.class,
                    profile.getUserId(),
                    profile.getBio(),
                    profile.getLocation(),
                    profile.getExperience(),
                    profile.getSpecialties(),
                    profile.getBirthPreferences());
                    profile.getLatitude();
                    profile.getLongitude();

            return getProfileById(profileId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    private Profile getProfileById(int profileId) {
        String sql = "SELECT * FROM profiles WHERE profile_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, profileId);
            if (results.next()) {
                return mapRowToProfile(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return null;
    }

    @Override
    public Profile updateProfile(Profile profile) {
        String sql = "UPDATE profiles SET bio = ?, location = ?, experience = ?, specialties = ?, birth_preferences = ?, latitude = ?, longitude = ? " +
                "WHERE user_id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql,
                    profile.getBio(),
                    profile.getLocation(),
                    profile.getExperience(),
                    profile.getSpecialties(),
                    profile.getBirthPreferences(),
                    profile.getLatitude(),
                    profile.getLongitude(),
                    profile.getUserId());

            if (rowsAffected == 0) {
                throw new DaoException("No profile found to update for user ID: " + profile.getUserId());
            }
            return getProfileByUserId(profile.getUserId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public Profile saveProfile(Profile profile) {
        String sql = "INSERT INTO profiles (user_id, bio, location, experience, specialties, birth_preferences, latitude, longitude) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING profile_id";
        try {
            Integer profileId = jdbcTemplate.queryForObject(sql, Integer.class,
                    profile.getUserId(),
                    profile.getBio(),
                    profile.getLocation(),
                    profile.getExperience(),
                    profile.getSpecialties(),
                    profile.getBirthPreferences(),
                    profile.getLatitude(),
                    profile.getLongitude());

            return getProfileById(profileId);  // Return the saved profile
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }


    @Override
    public boolean deleteProfileByUserId(int userId) {
        String sql = "DELETE FROM profiles WHERE user_id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, userId);
            if (rowsAffected == 0) {
                throw new DaoException("No profile found to delete for user ID: " + userId);
            }
            return true;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
    }

    @Override
    public List<Profile> findNearbyProfessionals() {
        List<Profile> profiles = new ArrayList<>();
        String sql = "SELECT p.*, u.username " +
                "FROM profiles p " +
                "JOIN users u ON p.user_id = u.user_id " +
                "WHERE u.role = 'ROLE_PROFESSIONAL' " +
                "AND p.latitude IS NOT NULL " +
                "AND p.longitude IS NOT NULL " +
                "LIMIT 10";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                profiles.add(mapRowToProfile(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return profiles;
    }
    private Profile mapRowToProfile(SqlRowSet rs) {
        Profile profile = new Profile();
        profile.setProfileId(rs.getInt("profile_id"));
        profile.setName(rs.getString("name"));
        profile.setUserId(rs.getInt("user_id"));
        profile.setBio(rs.getString("bio"));
        profile.setLocation(rs.getString("location"));
        profile.setExperience(rs.getString("experience"));
        profile.setSpecialties(rs.getString("specialties"));
        profile.setBirthPreferences(rs.getString("birth_preferences"));
        profile.setLatitude(rs.getDouble("latitude"));
        profile.setLongitude(rs.getDouble("longitude"));
        return profile;
    }
}
