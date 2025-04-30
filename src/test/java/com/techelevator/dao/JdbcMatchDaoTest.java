package com.techelevator.dao;

import com.techelevator.model.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcMatchDaoTest extends BaseDaoTest {
    protected static final Match MATCH_1 = new Match(1, 1, 3, "MATCHED");
    protected static final Match MATCH_2 = new Match(2, 2, 4, "MATCHED");
    private JdbcMatchDao dao;

    @BeforeEach
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        dao = new JdbcMatchDao(jdbcTemplate);
    }

    @Test
    public void getMatchById_invalidId_returnsNull() {
        Match match = dao.getMatchById(-1);
        assertNull(match, "Expected null for invalid match ID");
    }

    @Test
    public void createMatch_validData_createsMatch() {
        Match newMatch = new Match(1, 4, "PENDING");
        Match createdMatch = dao.createMatch(newMatch);

        assertNotNull(createdMatch, "Expected non-null match after creation");
        assertEquals("PENDING", createdMatch.getStatus(), "Expected status to be PENDING");
    }

    @Test
    public void getMatchesForUser_validUser_returnsMatches() {
        List<Match> matches = dao.getMatchesForUser(1);

        assertNotNull(matches, "Expected non-null list of matches");
        assertFalse(matches.isEmpty(), "Expected at least one match for user");
    }

    @Test
    public void deleteMatch_validId_deletesSuccessfully() {
        int matchId = 1;
        assertTrue(dao.deleteMatch(matchId), "Expected delete to return true");
        assertNull(dao.getMatchById(matchId), "Deleted match should not exist");
    }
}

