package com.finish;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.questions.EnglishActivity;
import com.questions.MathQuestions;

import login.lawersinc.com.lawersinc.R;

public class Finish extends AppCompatActivity {

    String examType;
    Integer  level;
    Button posButton;
    TextView posTextView;
    ImageView posImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_positive_end_page);

        posButton = (Button)findViewById(R.id.posButton);
        posTextView = (TextView) findViewById(R.id.posTextView);
        posImageView = (ImageView) findViewById(R.id.positiveImageView);

        Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String passOrFail = extras.getString("pass");
            examType = extras.getString("examType");
            level = extras.getInt("level", 0);
            if(passOrFail.equalsIgnoreCase("true")){

                String uri = "@drawable/tick_mark";
                int imageResource = getResources().getIdentifier(uri, null, getPackageName());

                Drawable res = getResources().getDrawable(imageResource);
                posImageView.setImageDrawable(res);

                posTextView.setText("Congrats!");
                posImageView.setImageDrawable(getResources().getDrawable(imageResource));


            }
            else {

                String uri = "@drawable/cross";
                int imageResource = getResources().getIdentifier(uri, null, getPackageName());

                Drawable res = getResources().getDrawable(imageResource);
                posImageView.setImageDrawable(res);

                posTextView.setText("Return to Login!");
                posImageView.setImageDrawable(getResources().getDrawable(imageResource));

            }

            posButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (examType.equalsIgnoreCase("english")) {
                        //EnglishActivity.level++;
                        Intent i = new Intent(getApplicationContext(), EnglishActivity.class);
                    } else {
                        Toast.makeText(getApplicationContext(), examType + level, Toast.LENGTH_LONG).show();
                        MathQuestions.level++;
                        Intent i = new Intent(getApplicationContext(), MathQuestions.class);
                        startActivity(i);
                    }
                }
            });
        }
    }

}
