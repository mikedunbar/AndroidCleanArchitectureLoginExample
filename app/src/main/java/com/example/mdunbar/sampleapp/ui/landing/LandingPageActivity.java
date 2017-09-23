package com.example.mdunbar.sampleapp.ui.landing;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
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
        logIdWithoutPhonePerm();
        logIdWithPhonePerm();
    }

    private void logIdWithPhonePerm() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            requestPermission();
            return;
        }
        TelephonyManager tManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String id = tManager.getDeviceId();
        Log.i("ID-TAG", "ID with phone perm: " + id);
    }

    private void logIdWithoutPhonePerm() {
        String id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.i("ID-TAG", "ID without phone perm: " + id);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    logIdWithPhonePerm();
                }
            }
        }
    }
}
