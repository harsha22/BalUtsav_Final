package com.lawersinc.level;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.questions.EnglishActivity;
import com.questions.MathQuestions;

import login.lawersinc.com.lawersinc.R;

public class Level extends AppCompatActivity {

    Button engButton, mathsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_test);
        engButton = (Button)findViewById(R.id.TakeEnglish);
        mathsButton = (Button)findViewById(R.id.TakeMaths);

        engButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level.this, EnglishActivity.class);
                startActivity(intent);
            }
        });

        mathsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level.this, MathQuestions.class);
                startActivity(intent);
            }
        });
    }

}
