package repository.database;

import domain.Friendship;
import domain.FriendshipState;
import repository.Repository;
import repository.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendshipDBRepository implements Repository<Friendship> {
    private final String url;
    private final String username;
    private final String password;

    public FriendshipDBRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Friendship addOne(Friendship toAdd) throws RepositoryException {
        String sql = "INSERT INTO friendships (\"ID\", \"User1\", \"User2\", \"FriendsFrom\", \"FriendshipState\") values (?, ?, ?, ?, ?)";
        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, toAdd.getID());
            statement.setInt(2, toAdd.getUser1());
            statement.setInt(3, toAdd.getUser2());
            statement.setTimestamp(4, Timestamp.valueOf(toAdd.getDateCreated()));
            statement.setString(5, String.valueOf(toAdd.getFriendshipState()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Friendship> addAll(List<Friendship> toAdd) throws RepositoryException {
        return null;
    }

    @Override
    public Friendship getOne(Integer ID) throws RepositoryException {
        String sql = "SELECT * from friendships WHERE \"ID\" = ?";
        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, ID);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                return new Friendship(result.getInt(1), result.getInt(2), result.getInt(3), result.getTimestamp(4).toLocalDateTime(), FriendshipState.valueOf(result.getString(5)));
            }
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Friendship> getAll() {
        String sql = "SELECT * from friendships";
        List<Friendship> friendships = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                Friendship friendship = new Friendship(result.getInt(1), result.getInt(2), result.getInt(3), result.getTimestamp(4).toLocalDateTime(), FriendshipState.valueOf(result.getString(5)));
                friendships.add(friendship);
            }
            return friendships;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateOne(Friendship toUpdate) throws RepositoryException {
        String sql = "UPDATE friendships SET \"ID\" = ?, \"User1\" = ?, \"User2\" = ?, \"FriendsFrom\" = ?, \"FriendshipState\" = ?  WHERE \"ID\" = ?";
        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, toUpdate.getUser1());
            statement.setInt(2, toUpdate.getUser2());
            statement.setTimestamp(3, Timestamp.valueOf(toUpdate.getFriendsFrom()));
            statement.setString(4, String.valueOf(toUpdate.getFriendshipState()));
            statement.setInt(5, toUpdate.getID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAll(List<Friendship> toUpdate) {

    }

    @Override
    public Friendship deleteOne(Integer ID) {
        String sql = "DELETE FROM friendships WHERE \"ID\" = ?";
        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, ID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Friendship> deleteAll(Integer[] ID) {
        return null;
    }

    @Override
    public void clear() {
        String sql = "DELETE FROM friendships";
        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int size() {
        return 0;
    }
}
