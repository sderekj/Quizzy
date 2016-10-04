package com.example.c4q.quizzy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CHEAT_REQUEST = 111;
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button prevButton;
    private Button cheatButton;
    private TextView quizTakerName;
    private TextView questionTextView;

    public Question[] questionBank;
    private int currentIndex = 0;
    private int score = 0;

    public void initializeQuestions() {
        questionBank = new Question[]{
                new Question(R.string.question_static, false),
                new Question(R.string.question_private_class, true),
                new Question(R.string.question_method, false),
                new Question(R.string.question_class_access, false),
                new Question(R.string.question_class_instance_variable, true),
                new Question(R.string.question_super, true),
                new Question(R.string.question_restrictive_access_level, false)
        };
        updateQuestion();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.EXTRA_NAME);
        TextView usernameSpace = (TextView) findViewById(R.id.quizzer_name);
        usernameSpace.setText("Quiz for: " + name);


        initializeViews(); //find view by id methods
        initializeQuestions(); // loads Question objects into array of questions called questionBank
        initializeListeners(); //sets onClickListeners for buttton views.
    }


    public void initializeViews() {
        trueButton = (Button) findViewById(R.id.true_btn);
        falseButton = (Button) findViewById(R.id.false_btn);
        questionTextView = (TextView) findViewById(R.id.question_text_view);
        nextButton = (Button) findViewById(R.id.next_btn);
        prevButton = (Button) findViewById(R.id.prev_btn);
        quizTakerName = (TextView) findViewById(R.id.quizzer_name);
        cheatButton = (Button) findViewById(R.id.cheat_button);
        resetButtonColors();
    }


    public void initializeListeners() {
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        cheatButton.setOnClickListener(this);
    }


    public void resetButtonColors() {
        trueButton.setBackgroundResource(android.R.drawable.btn_default);
        falseButton.setBackgroundResource(android.R.drawable.btn_default);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //fixme - implement restartQuiz and add a way to save the quiz taker's score
        switch (item.getItemId()) {
            case R.id.restart_quiz_action:
                restartQuiz();
                updateQuestion();
                resetButtonColors();
                score = 0;
                break;
            case R.id.save_score:
                Toast.makeText(this, "Your score is " + score, Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }


    public void updateQuestion() {
        if (currentIndex >= 0 && currentIndex < questionBank.length) {
            Question currentQuestion = questionBank[currentIndex];
            int textResId = currentQuestion.getTextResId();
            questionTextView.setText(textResId);
        } else {
            currentIndex = 0;
            Toast.makeText(this, "Out of bounds Exception. Resetting index to Zero", Toast.LENGTH_SHORT).show();
        }
    }


    //fixme
    public void restartQuiz() {
        Toast.makeText(this, "Quiz Restarted", Toast.LENGTH_LONG).show();
        currentIndex = 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHEAT_REQUEST) {
            Toast.makeText(this, "You cheated!!!", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Your Result Code was " + CHEAT_REQUEST, Toast.LENGTH_SHORT).show();
        }
    }

    public Question getCurrentQuestion() {
        return questionBank[currentIndex];
    }


    @Override  //overriding on click method of OnClickListener interface.
    public void onClick(View v) {

        resetButtonColors();
        switch (v.getId()) {
            case R.id.true_btn:
                Question question = getCurrentQuestion();
                if (question.isAnswerTrue()) {
                    falseButton.setBackgroundResource(R.color.red);
                    trueButton.setBackgroundResource(R.color.green);
                    score++;
                } else {
                    falseButton.setBackgroundResource(R.color.green);
                    trueButton.setBackgroundResource(R.color.red);
                }
                Toast.makeText(this, "You've answered " + score + " out of " + questionBank.length + " correct", Toast.LENGTH_LONG).show();
                break;
            case R.id.false_btn:
                Question questionn = getCurrentQuestion();
                if (questionn.isAnswerTrue()) {
                    falseButton.setBackgroundResource(R.color.red);
                    trueButton.setBackgroundResource(R.color.green);
                } else {
                    falseButton.setBackgroundResource(R.color.green);
                    score++;
                    trueButton.setBackgroundResource(R.color.red);
                }
                Toast.makeText(this, "You've answered " + score + " out of " + questionBank.length + " correct", Toast.LENGTH_LONG).show();
                break;
            case R.id.prev_btn:
                currentIndex--;
                updateQuestion();
                break;
            case R.id.next_btn:
                currentIndex++;
                updateQuestion();
                break;
            case R.id.cheat_button:
                Toast.makeText(this, "Showing cheat mode",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
                // add extras to this
                intent.putExtra("CURRENT INDEX", currentIndex);

                /* Same thing as above
                Question currentQuestion = questionBank[currentIndex];
                String questionStr = getResources().getString(currentQuestion.getTextResId());
                intent.putExtra("CURRENT QUESTION", questionStr);
                boolean answer = currentQuestion.isAnswerTrue();
                intent.putExtra("CURRENT ANSWER",answer);
                */

                startActivityForResult(intent, CHEAT_REQUEST);
                break;
        }
    }
}
