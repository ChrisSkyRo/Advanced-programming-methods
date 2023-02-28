package com.example.multipleoptionstest;

import com.example.multipleoptionstest.domain.Question;
import com.example.multipleoptionstest.repo.QuestionsRepo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.List;

public class HelloController {
    private QuestionsRepo questionsRepo;
    private List<VBox> studentWindows;
    private int maxScore;
    private int score1, score2, score3;
    
    public HelloController() {
        questionsRepo = new QuestionsRepo("jdbc:postgresql://localhost:5432/MultipleChoice", "postgres", "wesnoth4");
        studentWindows = new ArrayList<>();
        maxScore = 0;
        score1 = 0;
        score2 = 0;
        score3 = 0;
    }
    
    public void CreateTeacherWindow(Stage stage) throws IOException {
        List<Question> questionList = questionsRepo.getAll();
        ObservableList<Question> questionsTable = FXCollections.observableArrayList(questionList);
        
        VBox layout = new VBox(10);

        // Questions Table
        TableView<Question> tableView = new TableView<>();
        tableView.setEditable(true);
        
        TableColumn<Question, String> column1 = new TableColumn<>("Question");
        column1.setCellValueFactory(new PropertyValueFactory<Question, String>("qdescription"));
        
        tableView.setItems(questionsTable);
        tableView.getColumns().add(column1);
        
        layout.getChildren().add(tableView);
        
        // Button
        Button placeQuestion = new Button("Place question");

        Label maxScoreLabel = new Label();
        Label score1Label = new Label();
        Label score2Label = new Label();
        Label score3Label = new Label();
        
        EventHandler<ActionEvent> placeEvent = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Question askedQuestion = tableView.getSelectionModel().getSelectedItem();
                if(askedQuestion != null) {
                    tableView.getItems().remove(askedQuestion);

                    maxScore += askedQuestion.getScore();
                    maxScoreLabel.setText("Max Score: " + Integer.toString(maxScore));

                    for (int i = 0; i < studentWindows.size(); i++) {
                        VBox studentLayout = studentWindows.get(i);
                        VBox newLayout = new VBox(10);
                        Label newQuestion = new Label(askedQuestion.getDescription());
                        newLayout.getChildren().add(newQuestion);

                        ToggleGroup tg = new ToggleGroup();
                        RadioButton r1 = new RadioButton(askedQuestion.getAnswer1());
                        RadioButton r2 = new RadioButton(askedQuestion.getAnswer2());
                        RadioButton r3 = new RadioButton(askedQuestion.getAnswer3());
                        r1.setToggleGroup(tg);
                        r2.setToggleGroup(tg);
                        r3.setToggleGroup(tg);
                        newLayout.getChildren().add(r1);
                        newLayout.getChildren().add(r2);
                        newLayout.getChildren().add(r3);

                        Button sendAnswer = new Button("Send answer");

                        int finalI = i;
                        EventHandler<ActionEvent> sendAnswerEvent = new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                if (r1.isSelected() || r2.isSelected() || r3.isSelected()) {
                                    r1.setDisable(true);
                                    r2.setDisable(true);
                                    r3.setDisable(true);
                                    sendAnswer.setDisable(true);

                                    int selected = 1;
                                    if (r2.isSelected())
                                        selected = 2;
                                    else if (r3.isSelected())
                                        selected = 3;

                                    if (askedQuestion.getCorrectAnswer() == 1) {
                                        r1.setTextFill(Color.GREEN);
                                        r2.setTextFill(Color.RED);
                                        r3.setTextFill(Color.RED);
                                    } else if (askedQuestion.getCorrectAnswer() == 2) {
                                        r2.setTextFill(Color.GREEN);
                                        r1.setTextFill(Color.RED);
                                        r3.setTextFill(Color.RED);
                                    } else {
                                        r3.setTextFill(Color.GREEN);
                                        r1.setTextFill(Color.RED);
                                        r2.setTextFill(Color.RED);
                                    }

                                    if (selected == askedQuestion.getCorrectAnswer()) {
                                        if (finalI == 0) {
                                            score1 += askedQuestion.getScore();
                                            score1Label.setText("Student1 score: " + Integer.toString(score1));
                                        } else if (finalI == 1) {
                                            score2 += askedQuestion.getScore();
                                            score2Label.setText("Student2 score: " + Integer.toString(score2));
                                        } else {
                                            score3 += askedQuestion.getScore();
                                            score3Label.setText("Student3 score: " + Integer.toString(score3));
                                        }
                                    }
                                }
                            }
                        };
                        sendAnswer.setOnAction(sendAnswerEvent);

                        newLayout.getChildren().add(sendAnswer);

                        studentLayout.getChildren().add(newLayout);
                    }
                }
            }
        };
        placeQuestion.setOnAction(placeEvent);
        
        layout.getChildren().add(placeQuestion);
        
        layout.getChildren().addAll(maxScoreLabel, score1Label, score2Label, score3Label);
        
        
        Scene scene = new Scene(layout, 640, 480);
        stage.setTitle("Evaluator");
        stage.setScene(scene);
        stage.setX(100);
        stage.setY(50);
        stage.show();
    }
    
    public void CreateStudentWindow(String name, double X, double Y) {
        VBox layout = new VBox(10);
        Scene secondScene = new Scene(layout, 640, 480);
        Stage newWindow = new Stage();
        newWindow.setTitle(name);
        newWindow.setScene(secondScene);
        newWindow.setX(X);
        newWindow.setY(Y);
        newWindow.show();
        studentWindows.add(layout);
    }
}