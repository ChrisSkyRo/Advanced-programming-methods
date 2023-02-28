package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Friendship extends NetworkEntity {
    private LocalDateTime friendsFrom;
    private Integer user1;
    private Integer user2;
    private FriendshipState friendshipState;

    public Friendship(Integer ID, Integer user1, Integer user2, LocalDateTime friendsFrom, FriendshipState friendshipState) {
        this.ID = ID;
        this.user1 = user1;
        this.user2 = user2;
        this.friendsFrom = friendsFrom;
        this.friendshipState = friendshipState;
    }

    public LocalDateTime getFriendsFrom() {
        return friendsFrom;
    }

    public void setFriendsFrom(LocalDateTime friendsFrom) {
        this.friendsFrom = friendsFrom;
    }

    public Integer getUser1() {
        return user1;
    }

    public void setUser1(Integer user1) {
        this.user1 = user1;
    }

    public Integer getUser2() {
        return user2;
    }

    public void setUser2(Integer user2) {
        this.user2 = user2;
    }

    /**
     * @return the date the friendship was created on
     */
    public LocalDateTime getDateCreated() {
        return friendsFrom;
    }

    public FriendshipState getFriendshipState() {
        return friendshipState;
    }

    public void setFriendshipState(FriendshipState friendshipState) {
        this.friendshipState = friendshipState;
    }

    @Override
    public String toString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "Friendship{" + getID() + "; " + getUser1() + "; " + getUser2() + "; " + getDateCreated().format(dateTimeFormatter) + "; " + "}";
    }
}
