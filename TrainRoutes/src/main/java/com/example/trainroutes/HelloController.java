package com.example.trainroutes;

import com.example.trainroutes.domain.City;
import com.example.trainroutes.domain.TrainStation;
import com.example.trainroutes.repository.CityRepo;
import com.example.trainroutes.repository.TrainStationRepo;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class HelloController {
    private Integer lastWindowX;
    private Integer lastWindowY;
    private CityRepo cityRepo;
    private TrainStationRepo trainStationRepo;
    private List<List<TrainStation>> RoutesList;
    private List<TrainStation> bktList;
    private int PRICE_PER_STATION = 10;
    
    public HelloController() {
        this.lastWindowX = 0;
        this.lastWindowY = 0;
        cityRepo = new CityRepo("jdbc:postgresql://localhost:5432/TrainRoutes", "postgres", "wesnoth4");
        trainStationRepo = new TrainStationRepo("jdbc:postgresql://localhost:5432/TrainRoutes", "postgres", "wesnoth4");
        RoutesList = new ArrayList<>();
        bktList = new ArrayList<>();
    }
    
    protected boolean verify(boolean direct) {
        int last = bktList.size()-1;
        
        if(bktList.size() > 1) {
            // checks for connection between routes
            if (bktList.get(last).getDepartureCityId() != bktList.get(last-1).getDestinationCityId())
                return false;

            // checks for direct route
            if (direct && bktList.get(last).getTrainId() != bktList.get(last-1).getTrainId())
                return false;
        }
        
        // checks for duplicates
        for (int i = 0; i < last; i++)
            if (bktList.get(i).equals(bktList.get(last)))
                return false;
        
        return true;
    }
    
    protected boolean solution(int departure, int destination) {
        // checks for departure
        if (bktList.get(0).getDepartureCityId() != departure)
            return false;
        
        // checks for destination
        if (bktList.get(bktList.size()-1).getDestinationCityId() != destination)
            return false;
        
        // check for connections
        for (int i = 1; i < bktList.size()-1; i++)
            if(bktList.get(i).getDepartureCityId() != bktList.get(i-1).getDestinationCityId())
                return false;
        
        return true;
    }
    
    protected void BKT(int departure, int destination, List<TrainStation> stations, boolean direct) {
        for (TrainStation station : stations) {
            bktList.add(station);
            if (verify(direct)) {
                if (solution(departure, destination)) {
                    List<TrainStation> newRoute = new ArrayList<>();
                    newRoute.addAll(bktList);
                    RoutesList.add(newRoute);
                }
                else BKT(departure, destination, stations, direct);
            }
            bktList.remove(station);
        }
    }
    
    protected int CityNameToId(String cityName, List<City> cities) {
        for (City city : cities)
            if(city.getName().equals(cityName))
                return city.getId();
        
        return -1337;
    }
    
    protected String CityIdToName(int cityId, List<City> cities) {
        for (City city : cities)
            if(city.getId() == cityId)
                return city.getName();

        return null;
    }
    
    @FXML
    protected void NewClientWindow() {
        VBox secondaryLayout = new VBox(10);
        List<String> cityNames = new ArrayList<>();
        List<City> cities = cityRepo.getAll();
        for (City city: cities) {
            cityNames.add(city.getName());
        }

        HBox options = new HBox(10);
        
        // New Window Elements
        Label departureLabel = new Label("Departure City: ");
        options.getChildren().add(departureLabel);
        
        ComboBox departure = new ComboBox(FXCollections.observableArrayList(cityNames));
        options.getChildren().add((departure));

        Label destinationLabel = new Label("Destination City: ");
        options.getChildren().add(destinationLabel);
        
        ComboBox destination = new ComboBox(FXCollections.observableArrayList(cityNames));
        options.getChildren().add((destination));

        CheckBox directRoutes = new CheckBox("Direct Routes Only");
        options.getChildren().add(directRoutes);

        Button search = new Button("Search");
        
        Label others = new Label("Other users are watching");

        VBox routeLabels = new VBox();
        
        // action event
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                routeLabels.getChildren().clear();
                        
                if(departure.getValue() != null && destination.getValue() != null) {
                    // get the chosen options
                    String depStr = departure.getValue().toString();
                    String destStr = destination.getValue().toString();

                    // find the ids of the cities
                    int depId = CityNameToId(depStr, cities);
                    int destId = CityNameToId(destStr, cities);

                    // get train stations
                    List<TrainStation> trainStations = trainStationRepo.getAll();

                    boolean directRoute = directRoutes.isSelected();
                    bktList.clear();
                    RoutesList.clear();
                    BKT(depId, destId, trainStations, directRoute);
                    
                    for (List<TrainStation> stations : RoutesList) {
                        String printString = "";
                        for(int i = 0; i < stations.size(); i++)
                            printString += CityIdToName(stations.get(i).getDepartureCityId(), cities) + "-" + stations.get(i).getTrainId() + "->";
                        printString += CityIdToName(stations.get(stations.size()-1).getDestinationCityId(), cities) 
                                + ", price: " + stations.size()*PRICE_PER_STATION;
                        
                        Label newRoute = new Label(printString);
                        if(stations.size()*PRICE_PER_STATION < 30)
                            newRoute.setTextFill(Color.GREEN);
                        routeLabels.getChildren().add(newRoute);
                    }
                }
            }
        };
        
        search.setOnAction(event);
        options.getChildren().add(search);
        options.getChildren().add(others);
        secondaryLayout.getChildren().add(options);
        secondaryLayout.getChildren().add(routeLabels);
        
        Scene secondScene = new Scene(secondaryLayout, 800, 400);

        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Client Window");

        // Position of the new window
        newWindow.setX(lastWindowX);
        newWindow.setY(lastWindowY);
        
        lastWindowX += 800;
        if(lastWindowX > 800) {
            lastWindowX = 0;    
            lastWindowY += 400;
        }
        if(lastWindowY > 800){
            lastWindowY = 0;
            lastWindowX = 0;
        }

        newWindow.setScene(secondScene);
        newWindow.show();
    }
}