package com.example.c4q.quizzy;

public class Question {

    private int textResId;
    private boolean answer;

    public Question(int textResId, boolean answer){
        this.textResId = textResId;
        this.answer = answer;
    }

    public int getTextResId() {
        return textResId;
    }

//    public void setAnswer(boolean answer) {
//        this.answer = answer;
//    }

    public boolean isAnswerTrue() {
        return answer;
    }


}
