package service;

import domain.User;
import domain.validators.UserValidator;
import domain.validators.ValidatorStrategy;
import repository.database.UserDBRepository;
import repository.file.UserFileRepository;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private Integer currentID;
    private final UserDBRepository repo;
    private final UserValidator userValidator;

    public UserService() {
        userValidator = new UserValidator();
        this.repo = new UserDBRepository("jdbc:postgresql://localhost:5432/skynet", "postgres", "super");
        this.currentID = GetLastID()+1;
    }

    public List<String> ListUsers() {
        List<User> users = repo.getAll();
        List<String> usersList = new ArrayList<>();
        for (User user : users)
            usersList.add(user.toString());
        return usersList;
    }

    public List<Integer> GetUsers() {
        List<User> users = repo.getAll();
        List<Integer> usersList = new ArrayList<>();
        for (User user : users)
            usersList.add(user.getID());
        return usersList;
    }

    public String GetOneUser(Integer ID) {
        return repo.getOne(ID).toString();
    }

    public String AddUser(List<String> toAdd) {
        String error = "";
        User user = CreateUser(toAdd);
        List<User> existing = repo.getAll();
        for(User exu : existing) {
            if(exu.getEmail().equals(user.getEmail())) {
                return "User already exists";
            }
        }
        try {
            userValidator.validate(user, ValidatorStrategy.Extensive);
            repo.addOne(user);
        }
        catch (Exception e) {
            error += e.getMessage();
        }
        if(error.isEmpty())
            currentID++;
        return error;
    }

    public String RemoveUser(Integer ID) {
        String error = "";
        if(!UserExists(ID))
            error = "A user with the given ID doesn't exist";
        repo.deleteOne(ID);
        return error;
    }

    public String ModifyUser(List<String> toModify) {
        User user = CreateUser(toModify);
        String error = "";
        try {
            userValidator.validate(user, ValidatorStrategy.Extensive);
            repo.updateOne(user);
        }
        catch (Exception e) {
            error += e.getMessage();
        }
        return error;
    }

    public boolean UserExists(Integer ID) {
        return !(repo.getOne(ID) == null);
    }

    public Integer GetLastID() {
        List<User> users = repo.getAll();
        if(users.size() == 0)
            return 0;
        return users.get(users.size()-1).getID();
    }

    private User CreateUser(List<String> toCreate) {
        User user;
        if(toCreate.size() == 4) {
            String firstName = toCreate.get(0);
            String lastName = toCreate.get(1);
            String email = toCreate.get(2);
            String password = toCreate.get(3);
            user = new User(currentID, firstName, lastName, email, password);
        }
        else {
            Integer ID = Integer.parseInt(toCreate.get(0));
            String firstName = toCreate.get(1);
            String lastName = toCreate.get(2);
            String email = toCreate.get(3);
            String password = toCreate.get(4);
            user = new User(ID, firstName, lastName, email, password);
        }
        return user;
    }
}
