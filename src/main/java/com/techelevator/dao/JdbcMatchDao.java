package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Match;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcMatchDao implements MatchDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcMatchDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Match createMatch(Match match) {
        String sql = "INSERT INTO matches (user1_id, user2_id, status) VALUES (?, ?, ?) RETURNING match_id";
        try {
            String status = match.getStatus() != null ? match.getStatus() : "PENDING";
            Integer matchId = jdbcTemplate.queryForObject(sql, Integer.class,
                    match.getUser1Id(), match.getUser2Id(), match.getStatus());
            return getMatchById(matchId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public List<Match> getMatchesForUser(int userId) {
        List<Match> matches = new ArrayList<>();
        String sql = "SELECT * FROM matches WHERE user1_id = ? OR user2_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId);
            while (results.next()) {
                matches.add(mapRowToMatch(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return matches;
    }

    @Override
    public Match updateMatch(Match match) {
        String sql = "UPDATE matches SET status = ? WHERE match_id = ?";
        try {
            jdbcTemplate.update(sql, match.getStatus(), match.getMatchId());
            return getMatchById(match.getMatchId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public Match getMatchById(int matchId) {
        Match match = null;
        String sql = "SELECT * FROM matches WHERE match_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, matchId);
            if (results.next()) {
                match = mapRowToMatch(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return match;
    }

    @Override
    public boolean deleteMatch(int matchId) {
        String sql = "DELETE FROM matches WHERE match_id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, matchId);
            return rowsAffected > 0;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    private Match mapRowToMatch(SqlRowSet rs) {
        Match match = new Match(3, 7, "MATCHED");
        match.setMatchId(rs.getInt("match_id"));
        match.setUser1Id(rs.getInt("user1_id"));
        match.setUser2Id(rs.getInt("user2_id"));
        match.setStatus(rs.getString("status"));
        return match;
    }
}
