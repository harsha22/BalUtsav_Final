package com.lawersinc.loginflow.flow;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lawersinc.client.http.HttpOperations;

import java.util.Calendar;

import login.lawersinc.com.lawersinc.R;

/**
 * Created by mntiwari on 9/20/15.
 */
public class SignUpPUPDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public SignUpPUPDialog() {}

    static SignUpPUPDialog newInstance() {
        return new SignUpPUPDialog();
    }

    private FragmentActivity myContext;
    public EditText username;
    public static EditText dateOfBirth;
    public EditText email;
    public Button signUp;
    public EditText firstName,
                        lastName,
                        password,
                        confirmPassword;
    public String DOMAIN_URL = "http://www.adlance.in";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().getAttributes().windowAnimations = R.anim.slide_up;
        View view = inflater.inflate(R.layout.signup_popup_page, container);
        initializeFields(view);

        //date of birth dialog
        dateOfBirth = (EditText) view.findViewById(R.id.dateOfBirthPUP);
        getDialog().getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dateOfBirth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //DialogFragment newFragment = new DOBPickerFragment();
                    //newFragment.show(myContext.getSupportFragmentManager(), "datePicker");
                    showDatePickerDialog();
                }
            }
        });

        //first name validation
        firstName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if(!isValidName(firstName.getText().toString())) {
                        firstName.setError("Only alphabets allowed, Atleast 3 characters");
                    }
                }
            }
        });

        //lastname Validation
        lastName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if(!isValidName(lastName.getText().toString())) {
                        lastName.setError("Only alphabets allowed, Atleast 3 characters");
                    }
                }
            }
        });

        //username Validation
        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // check from database for validity of username
                }
            }
        });

        //Confirm password validation
        confirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (password==null || confirmPassword==null || !password.getText().toString().equals(confirmPassword.getText().toString()))
                        confirmPassword.setError("Password mismatch");
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "sign up", Toast.LENGTH_SHORT).show();
                HttpOperations httpOperations = new HttpOperations(DOMAIN_URL);

                JsonObjectRequest request = httpOperations.getRequest(DOMAIN_URL+"/clients/1");
                Volley.newRequestQueue(v.getContext()).add(request);

                System.out.println(request);
                Log.d("output", request.toString());
                Toast.makeText(v.getContext(), request.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        //email validation
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                extractEmail(v, hasFocus);
            }
        });

        return view;
    }

    private void initializeFields(View view) {
        signUp = (Button) view.findViewById(R.id.signUpPUP);
        username= (EditText) view.findViewById(R.id.usernamePUPET);
        firstName= (EditText) view.findViewById(R.id.firstNamePUPET);
        lastName= (EditText) view.findViewById(R.id.lastNamePUPET);
        email= (EditText) view.findViewById(R.id.emailAddressPUPET);
        password= (EditText) view.findViewById(R.id.passwordPUPPW);
        confirmPassword = (EditText) view.findViewById(R.id.confirmPasswordPUPPW);
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    public void validateSignUpFields(View view) {

    }

    public void extractDate(View view) {
        DialogFragment newFragment = new DOBPickerFragment();
        newFragment.show(myContext.getSupportFragmentManager(), "datePicker");
    }

    public void extractEmail(View view, boolean hasFocus) {
        if (!hasFocus) {
            if (!isValidEmail(email.getText().toString())) {
                email.setError("Invalid Email Address");
            }
        }
    }

    public void extractLastName(View view) {
    }

    public void extractUsername(View view) {
        username.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public void extractPassword(View view) {

    }

    //Display date picker dialog
    public void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        dateOfBirth.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
    }

    //email validation
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    //firstName Validation
    public final static boolean isValidName(CharSequence target) {
        return !TextUtils.isEmpty(target) && (target.length() >= 3) && (isAlpha(target.toString()));
    }

    public static boolean isAlpha(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }
}
