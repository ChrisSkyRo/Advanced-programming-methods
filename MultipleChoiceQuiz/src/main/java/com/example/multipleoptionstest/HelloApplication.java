package com.example.multipleoptionstest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        HelloController controller = new HelloController();
        // Teacher window
        controller.CreateTeacherWindow(stage);
        // 3 student windows
        controller.CreateStudentWindow("Student1", 100, 575);
        controller.CreateStudentWindow("Student2", 800, 50);
        controller.CreateStudentWindow("Student3", 800, 575);
    }

    public static void main(String[] args) {
        launch();
    }
}