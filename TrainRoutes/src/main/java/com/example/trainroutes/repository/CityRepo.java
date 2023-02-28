package com.example.trainroutes.repository;

import com.example.trainroutes.domain.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityRepo implements Repository<City>{
    private final String url;
    private final String username;
    private final String password;

    public CityRepo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public City addOne(City toAdd) throws RepositoryException {
        return null;
    }

    @Override
    public List<City> addAll(List<City> toAdd) throws RepositoryException {
        return null;
    }

    @Override
    public City getOne(Integer ID) throws RepositoryException {
        return null;
    }

    @Override
    public List<City> getAll() {
        String sql = "SELECT * from \"Cities\"";
        List<City> cities = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                City city = new City(result.getInt(1), result.getString(2));
                cities.add(city);
            }
            return cities;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateOne(City toUpdate) throws RepositoryException {

    }

    @Override
    public void updateAll(List<City> toUpdate) {

    }

    @Override
    public City deleteOne(Integer ID) {
        return null;
    }

    @Override
    public List<City> deleteAll(Integer[] ID) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public int size() {
        String sql = "SELECT COUNT(*) from Cities";
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
