import domain.User;
import repository.database.UserDBRepository;
import ui.UserInterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        UserInterface Skynet = new UserInterface();
        Skynet.run();
    }
}
