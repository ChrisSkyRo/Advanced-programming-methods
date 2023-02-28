package repository.database;

import domain.User;
import repository.Repository;
import repository.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDBRepository implements Repository<User> {
    private final String url;
    private final String username;
    private final String password;

    public UserDBRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public User addOne(User toAdd) throws RepositoryException {
        String sql = "INSERT INTO users (\"ID\", \"FirstName\", \"LastName\", email, password) values (?, ?, ?, ?, ?)";
        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, toAdd.getID());
            statement.setString(2, toAdd.getFirstName());
            statement.setString(3, toAdd.getLastName());
            statement.setString(4, toAdd.getEmail());
            statement.setString(5, toAdd.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> addAll(List<User> toAdd) throws RepositoryException {
        return null;
    }

    @Override
    public User getOne(Integer ID) throws RepositoryException {
        String sql = "SELECT * from users WHERE \"ID\" = ?";
        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, ID);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                return new User(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5));
            }
            return null;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT * from users";
        List<User> users = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                User user = new User(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateOne(User toUpdate) throws RepositoryException {
        String sql = "UPDATE users SET \"FirstName\" = ?, \"LastName\" = ?, \"email\" = ?, \"password\" = ?  WHERE \"ID\" = ?";
        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, toUpdate.getFirstName());
            statement.setString(2, toUpdate.getLastName());
            statement.setString(3, toUpdate.getEmail());
            statement.setString(4, toUpdate.getPassword());
            statement.setInt(5, toUpdate.getID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAll(List<User> toUpdate) {

    }

    @Override
    public User deleteOne(Integer ID) {
        String sql = "DELETE FROM users WHERE \"ID\" = ?";
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
    public List<User> deleteAll(Integer[] ID) {
        return null;
    }

    @Override
    public void clear() {
        String sql = "DELETE FROM users";
        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int size() {
        String sql = "SELECT COUNT(*) from users";
        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                return result.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
