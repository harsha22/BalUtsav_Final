package com.finish;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import login.lawersinc.com.lawersinc.R;

public class ChooseTest extends AppCompatActivity {
    Button mathChoice;
    Button engChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_test);
        mathChoice = (Button) findViewById(R.id.TakeMaths);
        mathChoice.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

            }
        });
        engChoice = (Button) findViewById(R.id.TakeEnglish);
        engChoice.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

            }
        });
    }
}
