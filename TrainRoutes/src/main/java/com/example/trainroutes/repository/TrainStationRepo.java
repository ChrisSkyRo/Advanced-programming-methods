package com.example.trainroutes.repository;

import com.example.trainroutes.domain.City;
import com.example.trainroutes.domain.TrainStation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainStationRepo implements Repository<TrainStation>{
    private final String url;
    private final String username;
    private final String password;

    public TrainStationRepo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public TrainStation addOne(TrainStation toAdd) throws RepositoryException {
        return null;
    }

    @Override
    public List<TrainStation> addAll(List<TrainStation> toAdd) throws RepositoryException {
        return null;
    }

    @Override
    public TrainStation getOne(Integer ID) throws RepositoryException {
        return null;
    }

    @Override
    public List<TrainStation> getAll() {
        String sql = "SELECT * from \"TrainStations\"";
        List<TrainStation> stations = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                TrainStation station = new TrainStation(result.getInt(1), result.getInt(2), result.getInt(3));
                stations.add(station);
            }
            return stations;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateOne(TrainStation toUpdate) throws RepositoryException {

    }

    @Override
    public void updateAll(List<TrainStation> toUpdate) {

    }

    @Override
    public TrainStation deleteOne(Integer ID) {
        return null;
    }

    @Override
    public List<TrainStation> deleteAll(Integer[] ID) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public int size() {
        String sql = "SELECT COUNT(*) from TrainStations";
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
