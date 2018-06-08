package com.dexterlearning.dexapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    Button signinButton;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        signinButton = (Button) findViewById(R.id.signinButton1);
        registerButton = (Button) findViewById(R.id.registerButton1);

        //sigin button should take user to the login activity
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this,
                        com.dexterlearning.dexapp.LoginActivity.class);

                startActivity(intent);
            }
        });

        //register button should take user to the register activity
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this,
                        com.dexterlearning.dexapp.RegisterActivity.class);

                startActivity(intent);
            }
        });
    }

}
