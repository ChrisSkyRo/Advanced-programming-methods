package ui;

import service.FriendshipService;
import service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private final UserService userService;
    private final FriendshipService friendshipService;
    public UserInterface() {
        this.userService = new UserService();
        this.friendshipService = new FriendshipService();
    }

    public void run(){
        Welcome();
        label:
        while(true){
            Scanner input = new Scanner(System.in);
            String command = input.nextLine();

            if(command.length() == 0)
                continue;

            switch (command) {
                case "exit":
                    Exit();
                    break label;
                case "help":
                    Help();
                    break;
                case "list users":
                    List<String> users = userService.ListUsers();
                    if(users.isEmpty())
                        System.out.println("There are no users registered");
                    else for(String user : users)
                        System.out.println(user);
                    break;
                case "list friendships":
                    List<String> friendships = friendshipService.ListFriendships();
                    if(friendships.isEmpty())
                        System.out.println("There are no friendships registered");
                    else for(String friendship : friendships)
                        System.out.println(friendship);
                    break;
                case "add user":
                    String result = userService.AddUser(ReadUser(ReadingStrategy.Add));
                    if(result.isEmpty())
                        Success();
                    else
                        System.out.println(result);
                    break;
                case "remove user":
                    Integer ID = ReadID();
                    result = userService.RemoveUser(ID);
                    if(result.isEmpty()) {
                        friendshipService.RemoveFriendshipsDeletedUser(ID);
                        Success();
                    }
                    else
                        System.out.println(result);
                    break;
                case "modify user":
                    result = userService.ModifyUser(ReadUser(ReadingStrategy.Modify));
                    if(result.isEmpty())
                        Success();
                    else
                        System.out.println(result);
                    break;
                case "add friendship":
                    friendships = ReadFriendship();
                    Integer user1 = Integer.parseInt(friendships.get(0));
                    Integer user2 = Integer.parseInt(friendships.get(1));
                    if(!userService.UserExists(user1) || !userService.UserExists(user2))
                        result = "Invalid friendship";
                    else
                        result = friendshipService.AddFriendship(friendships);
                    if(result.isEmpty()) {
                        Success();
                    }
                    else
                        System.out.println(result);
                    break;
                case "remove friendship":
                    ID = ReadID();
                    result = friendshipService.RemoveFriendship(ID);
                    if(result.isEmpty()) {
                        Success();
                    }
                    else
                        System.out.println(result);
                    break;
                case "communities count":
                    System.out.println(friendshipService.CountCommunities(userService.GetUsers()));
                    break;
                case "community top":
                    friendshipService.CountCommunities(userService.GetUsers());
                    List<Integer> friends = friendshipService.GetTopCommunity();
                    for(Integer friend : friends) {
                        System.out.println(userService.GetOneUser(friend));
                    }
                    break;
                default:
                    CommandError(command);
                    break;
            }
        }
    }

    private void Welcome() {
        System.out.println("Skynet - the not so social network ^_^");
        System.out.println("For a list of commands, type \"help\".");
    }

    private void Help() {
        System.out.println("""
                help - prints this menu
                list users - prints all the users registered
                list friendships - prints every friendship
                add user - adds a new user to the network
                remove user - removes an existing user from the network
                modify user - modifies an existing user
                add friendship - adds a new friendship to the network
                remove friendship - deletes an existing friendship from the network
                communities count - prints the number of communities
                community top - prints the community with the most users
                exit - closes the app
                """);
    }

    private void Exit() {
        System.out.println("Thank you for using Skynet! <3");
    }

    private void CommandError(String cmd) {
        System.out.println(cmd + " isn't recognized as a command. Please type a valid command.");
    }

    private void Success() {
        System.out.println("Operation executed successfully");
    }

    private Integer ReadID() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("ID = ");
        return scanner.nextInt();
    }

    private List<String> ReadUser(ReadingStrategy strategy) {
        List<String> user = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        if(strategy == ReadingStrategy.Modify) {
            System.out.print("ID: ");
            user.add(scanner.nextLine());
        }
        System.out.print("First name: ");
        user.add(scanner.nextLine());
        System.out.print("Last name: ");
        user.add(scanner.nextLine());
        System.out.print("Email: ");
        user.add(scanner.nextLine());
        System.out.print("Password: ");
        user.add(scanner.nextLine());
        return user;
    }

    private List<String> ReadFriendship() {
        List<String> friendship = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.print("First user: ");
        friendship.add(scanner.nextLine());
        System.out.print("Second user: ");
        friendship.add(scanner.nextLine());
        return friendship;
    }

}
