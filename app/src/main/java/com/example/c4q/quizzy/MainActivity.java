package com.example.c4q.quizzy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "com.example.c4q.quizzy.NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendName(View view) {
        Intent intent = new Intent(this, QuizActivity.class);
        EditText edittext = (EditText) findViewById(R.id.name_edit_text);
        String name = edittext.getText().toString();
        intent.putExtra(EXTRA_NAME, name);
        startActivity(intent);
    }
}
