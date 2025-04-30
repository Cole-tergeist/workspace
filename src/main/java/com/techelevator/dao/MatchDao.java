package com.techelevator.dao;

import com.techelevator.model.Match;
import java.util.List;

public interface MatchDao {
    Match createMatch(Match match);
    List<Match> getMatchesForUser(int userId);

    Match updateMatch(Match match);

    Match getMatchById(int matchId);
    boolean deleteMatch(int matchId);
}