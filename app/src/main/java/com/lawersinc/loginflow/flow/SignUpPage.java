package com.lawersinc.loginflow.flow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import login.lawersinc.com.lawersinc.R;

/**
 * Created by mntiwari on 9/17/15.
 */
public class SignUpPage extends AppCompatActivity {

    Button signUp, alreadyAMember;
    Animation animFadein;

    EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.signup_page);

        signUp = (Button) findViewById(R.id.signUpEmail);
        animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);
        alreadyAMember = (Button) findViewById(R.id.alreadyAMember);

    }

    public void displayESignUpPopUP(View view) {
        /*PopupWindow popup = new PopupWindow(SignUpPage.this);
        View layout = getLayoutInflater().inflate(R.layout.signup_popup_page, null);
        popup.setContentView(layout);
        // Set content width and height
        popup.setHeight(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        popup.setWidth(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Closes the popup window when touch outside of it - when looses focus
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        // Show anchored to button
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAsDropDown(view);*/

        FragmentManager fm = getSupportFragmentManager();
        SignUpPUPDialog editNameDialog = SignUpPUPDialog.newInstance();
        editNameDialog.show(fm, "signup_dialog");
    }

    public void alreadyAMemberOnClick(View view) {
        Intent intent = new Intent(SignUpPage.this, LoginPage.class);
        startActivity(intent);
        overridePendingTransition(R.anim.rotate_out, 0);
    }
}
