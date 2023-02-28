module com.example.trainroutes {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.trainroutes to javafx.fxml;
    exports com.example.trainroutes;
}