package com.example.emojigame;

public class User {
    private String username;
    private int point,questionNumber;

    public User(String username,int point,int questionNumber) {
        this.username = username;
        this.point=point;
        this.questionNumber=questionNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }
}
