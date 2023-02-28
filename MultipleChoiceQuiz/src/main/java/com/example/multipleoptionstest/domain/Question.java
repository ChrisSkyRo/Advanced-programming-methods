package com.example.multipleoptionstest.domain;

import javafx.beans.property.SimpleStringProperty;

public class Question extends BaseEntity {
    private int questionID;
    private String description;
    private String answer1;
    private String answer2;
    private String answer3;
    private int correctAnswer;
    private int score;
    
    private final SimpleStringProperty qdescription;

    public Question(int questionID, String description, String answer1, String answer2, String answer3, int correctAnswer, int score) {
        this.questionID = questionID;
        this.description = description;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.correctAnswer = correctAnswer;
        this.score = score;
        this.qdescription = new SimpleStringProperty(description);
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getQdescription() {
        return qdescription.get();
    }

    public SimpleStringProperty qdescriptionProperty() {
        return qdescription;
    }

    public void setQdescription(String qdescription) {
        this.qdescription.set(qdescription);
    }
}
