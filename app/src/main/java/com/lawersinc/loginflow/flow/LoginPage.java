package com.lawersinc.loginflow.flow;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.excelread.ExcelRead;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.lawersinc.bl.MainPage;
import com.lawersinc.level.Level;
import com.questions.EnglishActivity;
import com.questions.MathQuestions;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import login.lawersinc.com.lawersinc.R;

/**
 * Created by mntiwari on 9/17/15.
 */
public class LoginPage extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    Button switchBtt;

    Button loginWithFB;
    Button loginWithGmail;
    Button loginWithEmail;

    private enum loginFlow {
        facebook,
        gmail,
        email
    }

    private loginFlow whichLogin;

    //Gmail signin
    private static final int RC_SIGN_IN = 0;
    private GoogleApiClient mGoogleApiClient;
    /* Is there a ConnectionResult resolution in progress? */
    private boolean mIsResolving = false;
    /* Should we automatically resolve ConnectionResults when possible? */
    private boolean mShouldResolve = false;

    //FB login
    CallbackManager mCallbackManager;
    List<String> permissionNeeds= Arrays.asList("user_photos", "email", "user_birthday");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        whichLogin = loginFlow.email;
        //google signin
        // Build GoogleApiClient with access to basic profile
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .addScope(new Scope(Scopes.EMAIL))
                .build();


        //facebook sign in
        FacebookSdk.sdkInitialize(getApplicationContext());
        //swith to sign up page with animation
        switchBtt = (Button) findViewById(R.id.notAMemberBT);
        switchBtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, SignUpPage.class);
                startActivity(intent);
                overridePendingTransition(R.anim.rotate_out, 0);
            }
        });

        //login with facebook
        loginWithFB = (Button) findViewById(R.id.loginWithFB);
        loginWithFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichLogin = loginFlow.facebook;
                mCallbackManager = CallbackManager.Factory.create();
                LoginManager.getInstance().logInWithReadPermissions(
                        LoginPage.this,
                        permissionNeeds);
                LoginManager.getInstance().registerCallback(mCallbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResults) {
                                Toast.makeText(LoginPage.this, "Login Success", Toast.LENGTH_SHORT).show();
                                GraphRequest request = GraphRequest.newMeRequest(
                                        loginResults.getAccessToken(),
                                        new GraphRequest.GraphJSONObjectCallback() {
                                            @Override
                                            public void onCompleted(
                                                    JSONObject object,
                                                    GraphResponse response) {
                                                // Application code
                                                Log.d("FB Login", response.toString());
                                                Toast.makeText(LoginPage.this, response.toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                Bundle parameters = new Bundle();
                                parameters.putString("fields", "id,name,link");
                                request.setParameters(parameters);
                                request.executeAsync();
                            }

                            @Override
                            public void onCancel() {
                                Log.e("dd", "facebook login canceled");
                                Toast.makeText(LoginPage.this, "Login Cancel", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(FacebookException e) {
                                Log.e("dd", "facebook login failed error");
                                Toast.makeText(LoginPage.this, "Login Error", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        //login with Gmail
        loginWithGmail = (Button) findViewById(R.id.loginWithGmail);
        loginWithGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichLogin = loginFlow.gmail;
                onSignInClicked();
            }
        });

        //email login
        loginWithEmail = (Button) findViewById(R.id.loginWithEmail);
        loginWithEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichLogin = loginFlow.email;
                //Intent intent = new Intent(LoginPage.this, MainPage.class);
                Intent intent = new Intent(LoginPage.this, Level.class);
                //Intent intent = new Intent(LoginPage.this, EnglishActivity.class);
                startActivity(intent);

            }
        });

    }

    //FB Signin
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (whichLogin) {
            case facebook:
                mCallbackManager.onActivityResult(requestCode, resultCode, data);
                break;
            case gmail:
                if (requestCode == RC_SIGN_IN) {
                    // If the error resolution was not successful we should not resolve further.
                    if (resultCode != RESULT_OK) {
                        mShouldResolve = false;
                    }

                    mIsResolving = false;
                    mGoogleApiClient.connect();
                }

                break;
            case email:
                break;
        }
    }

    //Gmail Signin
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Could not connect to Google Play Services.  The user needs to select an account,
        // grant permissions or resolve an error in order to sign in. Refer to the javadoc for
        // ConnectionResult to see possible error codes.
        Log.d("Gmail signin", "onConnectionFailed:" + connectionResult);

        if (!mIsResolving && mShouldResolve) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this, RC_SIGN_IN);
                    mIsResolving = true;
                } catch (IntentSender.SendIntentException e) {
                    Log.e("Gmail signin", "Could not resolve ConnectionResult.", e);
                    mIsResolving = false;
                    mGoogleApiClient.connect();
                }
            } else {
                // Could not resolve the connection result, show the user an
                // error dialog.

                //showErrorDialog(connectionResult);
                Toast.makeText(getApplicationContext(), "connection failed", Toast.LENGTH_SHORT).show();
            }
        } else {
            //yet to implement
            //Show the signed-out UI
            //showSignedOutUI();
            Toast.makeText(getApplicationContext(), "signed out", Toast.LENGTH_SHORT).show();
        }
    }

    private void onSignInClicked() {
        // User clicked the sign-in button, so begin the sign-in process and automatically
        // attempt to resolve any errors that occur.
        mShouldResolve = true;
        mGoogleApiClient.connect();

        // Show a message to the user that we are signing in.
        //mStatus.setText(R.string.signing_in);
        //Toast.makeText(getApplicationContext(), "sign in success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnected(Bundle bundle) {
        // onConnected indicates that an account was selected on the device, that the selected
        // account has granted any requested permissions to our app and that we were able to
        // establish a service connection to Google Play services.
        Log.d("Gmail Signin", "onConnected:" + bundle);
        mShouldResolve = false;

        // Show the signed-in UI
        //showSignedInUI();
        Toast.makeText(getApplicationContext(), "account selected: "+Plus.PeopleApi.getCurrentPerson(mGoogleApiClient), Toast.LENGTH_SHORT).show();
        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            String personName = currentPerson.getDisplayName();
            String personPhoto = currentPerson.getImage().getUrl();
            String personGooglePlusProfile = currentPerson.getUrl();
            String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
            Toast.makeText(getApplicationContext(), personName, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(getApplicationContext(), "sign in failed: connection suspended", Toast.LENGTH_SHORT).show();
    }

    //yet to implement signout button
    private void onSignOutClicked() {
        // Clear the default account so that GoogleApiClient will not automatically
        // connect in the future.
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
        }

        //showSignedOutUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        switch (whichLogin) {
            case facebook:
                break;
            case gmail:
                mGoogleApiClient.connect();
                break;
            case email:
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        switch (whichLogin) {
            case facebook:
                break;
            case gmail:
                mGoogleApiClient.disconnect();
                break;
            case email:
                break;
        }
    }

}
