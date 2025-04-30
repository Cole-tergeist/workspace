package com.techelevator.controller;

import com.techelevator.dao.MatchDao;
import com.techelevator.model.Match;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/matches")
@CrossOrigin
public class MatchController {

    private final MatchDao matchDao;

    public MatchController(MatchDao matchDao) {
        this.matchDao = matchDao;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public Match createMatch(@RequestBody Match match) {
        return matchDao.createMatch(match);
    }

    @DeleteMapping
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMatch(@RequestParam int matchId) {
        boolean deleted = matchDao.deleteMatch(matchId);
        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found");
        }
    }

    @GetMapping("/{userId}")
    @PreAuthorize("isAuthenticated()")
    public List<Match> getMatchesForUser(@PathVariable int userId) {
        List<Match> matches = matchDao.getMatchesForUser(userId);
        if (matches == null || matches.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No matches found");
        }
        return matches;
    }
}
