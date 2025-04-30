package com.techelevator.model;

public class Friend {
    private int friendId;
    private int requesterId;
    private int addresseeId;
    private String status; // PENDING, ACCEPTED, DECLINED

    public Friend() {}

    public Friend(int friendId, int requesterId, int addresseeId, String status) {
        if (requesterId <= 0 || addresseeId <= 0) {
            throw new IllegalArgumentException("User IDs must be valid");
        }
        this.friendId = friendId;
        this.requesterId = requesterId;
        this.addresseeId = addresseeId;
        this.status = status;
    }

    // Getters and setters
    public int getFriendId() {
        return friendId;
    }
    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public int getRequesterId() {
        return requesterId;
    }
    public void setRequesterId(int requesterId) {
        this.requesterId = requesterId;
    }

    public int getAddresseeId() {
        return addresseeId;
    }
    public void setAddresseeId(int addresseeId) {
        this.addresseeId = addresseeId;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FriendRequestDto{" +
                "requesterId=" + requesterId +
                ", addresseeId=" + addresseeId +
                ", status='" + status + '\'' +
                '}';
    }
}
