package com.example.c4q.quizzy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CheatActivity extends QuizActivity {
    private static final int STATUS_CHEATED = 222;
    private Question currentQuestion;
    private Question[] copyOfQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = (Button) findViewById(R.id.cheat_button); // all of this can accessed because it extends QuizActivity
        setResult(STATUS_CHEATED);
        copyOfQuestions = super.questionBank;
        button.setVisibility(View.GONE);
        TextView questionTV = (TextView) findViewById(R.id.question_text_view);
        Intent intent = getIntent();

        /*
        String myQuestion = intent.getExtras().getString("CURRENT QUESTION");
        questionTV.setText(myQuestion);
        boolean myAnswer = intent.getExtras().getBoolean("CURRENT ANSWER");
        questionTV.setText(myQuestion + ": " + myAnswer);
        */

        int myIndex = intent.getExtras().getInt("CURRENT INDEX");
        currentQuestion = copyOfQuestions[myIndex];
        boolean answer = currentQuestion.isAnswerTrue();
        String questionText = getResources().getString(currentQuestion.getTextResId());
        questionTV.setText(questionText + ": " + answer);

//        questionTV.setText("CURRENT QUESTION");
    }
}
