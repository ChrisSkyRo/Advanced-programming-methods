package service;

import domain.Friendship;
import domain.FriendshipState;
import domain.validators.FriendshipValidator;
import domain.validators.ValidatorStrategy;
import repository.database.FriendshipDBRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendshipService {
    private Integer currentID;
    private final FriendshipDBRepository repo;
    private final List<Integer> topCommunity;
    private final FriendshipValidator friendshipValidator;

    public FriendshipService() {
        friendshipValidator = new FriendshipValidator();
        this.repo = new FriendshipDBRepository("jdbc:postgresql://localhost:5432/skynet", "postgres", "super");
        this.topCommunity = new ArrayList<>();
        this.currentID = GetLastID()+1;
    }

    public List<String> ListFriendships() {
        List<Friendship> friendships = repo.getAll();
        List<String> friendshipsList = new ArrayList<>();
        for (Friendship friendship : friendships)
            friendshipsList.add(friendship.toString());
        return friendshipsList;
    }

    public String AddFriendship(List<String> toAdd) {
        String error = "";
        Integer user1 = Integer.parseInt(toAdd.get(0));
        Integer user2 = Integer.parseInt(toAdd.get(1));
        if(user2 < user1) {
            Integer temp = user1;
            user1 = user2;
            user2 = temp;
        }
        List<Friendship> existing = repo.getAll();
        for(Friendship exFr : existing) {
            if(exFr.getUser1().equals(user1) && exFr.getUser2().equals(user2))
                return "Friendship already exists";
        }
        Friendship friendship = new Friendship(currentID, user1, user2, LocalDateTime.now(), FriendshipState.Pending);
        try {
            friendshipValidator.validate(friendship, ValidatorStrategy.Extensive);
            repo.addOne(friendship);
        }
        catch (Exception e) {
            error += e.getMessage();
        }
        if(error.isEmpty())
            currentID++;
        return error;
    }

    public String RemoveFriendship(Integer ID) {
        String error = "";
        if(repo.getOne(ID) == null)
            error = "A friendship with the given ID doesn't exist";
        repo.deleteOne(ID);
        return error;
    }

    public List<Integer> GetUsers(Integer ID) {
        List<Integer> users = new ArrayList<>();
        Friendship friendship = repo.getOne(ID);
        if(friendship != null) {
            users.add(friendship.getUser1());
            users.add(friendship.getUser2());
        }
        return users;
    }

    public Integer CountCommunities(List<Integer> users) {
        Integer communities = 0;
        int usersCount = users.size();
        Map<Integer, Boolean> visited = new HashMap<>();
        for(Integer user : users) {
            visited.put(user, false);
        }
        for(Integer user : users) {
            if(!visited.get(user)) {
                communities++;
                int[] Q = new int[usersCount];
                int left = 0, right = 1;
                Q[0] = user;
                while(left < right) {
                    visited.put(Q[left], true);
                    for (Friendship friendship : repo.getAll()) {
                        if (friendship.getUser1().equals(Q[left]) && !visited.get(friendship.getUser2()))
                            Q[right++] = friendship.getUser2();
                        else if (friendship.getUser2().equals(Q[left]) && !visited.get(friendship.getUser1()))
                            Q[right++] = friendship.getUser1();
                    }
                    left++;
                }
                if(right > topCommunity.size())
                    SetTopCommunity(Q, right);
            }
        }
        return communities;
    }

    private void SetTopCommunity(int[] Q, int right) {
        topCommunity.clear();
        for(int i = 0; i < right; i++)
            topCommunity.add(Q[i]);
    }

    public List<Integer> GetTopCommunity() {
        return topCommunity;
    }

    public Integer GetLastID() {
        List<Friendship> friendships = repo.getAll();
        if(friendships.size() == 0)
            return 0;
        return friendships.get(friendships.size()-1).getID();
    }

    public void RemoveFriendshipsDeletedUser(Integer ID) {
        List<Friendship> friendships = repo.getAll();
        List<Integer> toDelete = new ArrayList<>();
        for (Friendship friendship : friendships) {
            if(friendship.getUser1().equals(ID) || friendship.getUser2().equals(ID))
                toDelete.add(friendship.getID());
        }
        for(Integer delID : toDelete)
            repo.deleteOne(delID);
    }
}
