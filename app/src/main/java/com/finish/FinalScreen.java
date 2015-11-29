package com.finish;

import android.app.FragmentTransaction;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.questions.EnglishActivity;
import com.questions.MathQuestions;

import login.lawersinc.com.lawersinc.R;

public class FinalScreen extends AppCompatActivity implements PositiveEndPage.OnPosFragmentInteractionListener,  NegativeEndPage.OnNegFragmentInteractionListener {

    String examType;
    Integer  level;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_screen);
        Bundle extras = getIntent().getExtras();

        Fragment fragment;
        FragmentManager fm;
        if(extras != null)
        {
            String passOrFail = extras.getString("pass");
             examType = extras.getString("examType");
             level = extras.getInt("level", 0);
            if(passOrFail.equalsIgnoreCase("true")){
                fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    fragment =new PositiveEndPage();
                    ft.add(R.id.fragment_placeholder,fragment);
                    ft.commit();

            }
            else {
                fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    fragment =new NegativeEndPage();
                    ft.add(R.id.fragment_placeholder,fragment);
                    ft.commit();

            }
        }
    }

    @Override
    public void onNegFragmentInteraction(Uri uri) {
        //TODO harsha is loo
    }

    @Override
    public void onPosFragmentInteraction(Uri uri) {
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
}
