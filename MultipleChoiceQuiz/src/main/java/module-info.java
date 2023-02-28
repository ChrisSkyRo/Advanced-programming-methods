module com.example.multipleoptionstest {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.multipleoptionstest to javafx.fxml;
    opens com.example.multipleoptionstest.domain to java.base;
    exports com.example.multipleoptionstest;
    exports com.example.multipleoptionstest.domain;
}