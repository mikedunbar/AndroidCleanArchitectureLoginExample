package com.example.mdunbar.sampleapp.ui.landing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mdunbar.sampleapp.R;
import com.example.mdunbar.sampleapp.ui.login.view.LoginActivity;
import com.example.mdunbar.sampleapp.ui.viewcontacts.ViewContactsActivity;

/**
 * Landing page for app, displayed after a successful login.
 */
public class LandingPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        Button logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Since we don't maintain login state, we just go to the login page
                Intent loginIntent = new Intent(LandingPageActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        Button viewContactsButton = (Button) findViewById(R.id.view_contacts_screen_button);
        viewContactsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent viewContactsScreenIntent = new Intent(LandingPageActivity.this, ViewContactsActivity.class);
                startActivity(viewContactsScreenIntent);
            }
        });
    }
}
