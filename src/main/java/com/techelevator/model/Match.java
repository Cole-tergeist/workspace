package com.techelevator.model;

public class Match {
    private int matchId;
    private int user1Id;
    private int user2Id;
    private String status; //PENDING, MATCHED, DECLINED

    public Match() {
        this.status = "PENDING";
    }

    public Match(int user1Id, int user2Id, String status) {
        if (user1Id <= 0 || user2Id <= 0) {
            throw new IllegalArgumentException("User IDs must be valid (greater than 0)");
        }
        this.matchId = matchId;
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.status = status;
    }

    public Match(int matchId, int user1Id, int user2Id, String status) {
        this(user1Id, user2Id, status);
        this.matchId = matchId;
    }

    // Getters and setters
    public int getMatchId() {
        return matchId;
    }
    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getUser1Id() {
        return user1Id;
    }
    public void setUser1Id(int user1Id) {
        this.user1Id = user1Id;
    }

    public int getUser2Id() {
        return user2Id;
    }
    public void setUser2Id(int user2Id) {
        this.user2Id = user2Id;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MatchDto{" +
                "user1Id=" + user1Id +
                ", user2Id=" + user2Id +
                ", status='" + status + '\'' +
                '}';
    }
}
