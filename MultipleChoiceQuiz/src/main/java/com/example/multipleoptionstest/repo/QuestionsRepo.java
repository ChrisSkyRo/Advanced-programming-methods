package com.example.multipleoptionstest.repo;

import com.example.multipleoptionstest.domain.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionsRepo implements Repository<Question>{
    private final String url;
    private final String username;
    private final String password;

    public QuestionsRepo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Question addOne(Question toAdd) throws RepositoryException {
        return null;
    }

    @Override
    public List<Question> addAll(List<Question> toAdd) throws RepositoryException {
        return null;
    }

    @Override
    public Question getOne(Integer ID) throws RepositoryException {
        return null;
    }

    @Override
    public List<Question> getAll() {
        String sql = "SELECT * from \"Questions\"";
        List<Question> questions = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                Question question = new Question(result.getInt(1), result.getString(2), result.getString(3), 
                        result.getString(4), result.getString(5), result.getInt(6), result.getInt(7));
                questions.add(question);
            }
            return questions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateOne(Question toUpdate) throws RepositoryException {

    }

    @Override
    public void updateAll(List<Question> toUpdate) {

    }

    @Override
    public Question deleteOne(Integer ID) {
        return null;
    }

    @Override
    public List<Question> deleteAll(Integer[] ID) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public int size() {
        return 0;
    }
}
