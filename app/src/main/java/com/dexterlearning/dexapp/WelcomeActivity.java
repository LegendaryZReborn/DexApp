package com.dexterlearning.dexapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.design.widget.Snackbar;
import android.support.annotation.NonNull;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 123;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "Warning: ";

    private ProgressBar pbSignIn;
    private LinearLayout loginForm;

    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private Button signInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mAuth = FirebaseAuth.getInstance();
        pbSignIn = (ProgressBar) findViewById(R.id.pbSignIn);
        loginForm = (LinearLayout) findViewById(R.id.login_form_master);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton = (Button) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        showProgress(false);
        setHeaderLightBackground(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                showProgress(true);
                setHeaderLightBackground(true);
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                showProgress(false);
                setHeaderLightBackground(false);
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.sign_in_button:
                if(checkPlayService()) {
                    String  api = Integer.toString(GoogleApiAvailability
                            .GOOGLE_PLAY_SERVICES_VERSION_CODE);
                    Toast.makeText(this, api, Toast.LENGTH_SHORT);

                    signIn();
                }
                break;
        }
    }

    private void signIn() {
        mGoogleSignInClient.signOut();

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

     private void setHeaderLightBackground(boolean set){
         ConstraintLayout header = (ConstraintLayout) findViewById(R.id.header);
         ImageView imgvDexterLogo = (ImageView) findViewById(R.id.imgvDexterLogo);

         if(set) {
            header.setBackgroundColor(getResources().getColor(android.R.color.white));
            imgvDexterLogo.setImageResource(R.drawable.dextor_logo_vector);
        }else{
            header.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            imgvDexterLogo.setImageResource(R.drawable.dextor_logo_white_vector);
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(android.R.id.content),
                                    "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        //showProgress(false);
                        //setHeaderLightBackground(false);

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user){
        if(user != null) {
            Intent intent = new Intent(WelcomeActivity.this,
                    com.dexterlearning.dexapp.InstructorDashboardActivity.class);

            startActivity(intent);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
            loginForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            pbSignIn.setVisibility(show ? View.VISIBLE : View.GONE);
            pbSignIn.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    pbSignIn.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            pbSignIn.setVisibility(show ? View.VISIBLE : View.GONE);
            loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private boolean checkPlayService()
    {
        GoogleApiAvailability googleAvail = GoogleApiAvailability.getInstance();
        int playServiceStatus = googleAvail.isGooglePlayServicesAvailable(this);

        if(playServiceStatus != ConnectionResult.SUCCESS){
            if(googleAvail.isUserResolvableError(playServiceStatus)){
                googleAvail.getErrorDialog(WelcomeActivity.this,
                        playServiceStatus, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            }

            return false;
        }

       return true;
    }

}
