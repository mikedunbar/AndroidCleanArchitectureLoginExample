package com.example.mdunbar.sampleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Landing page for app, displayed after a successful login.
 */
public class LandingPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
    }
}
