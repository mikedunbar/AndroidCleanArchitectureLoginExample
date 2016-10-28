package com.example.mdunbar.sampleapp;

import android.app.Application;

import com.example.mdunbar.sampleapp.dagger.DaggerLoginComponent;
import com.example.mdunbar.sampleapp.dagger.LoginComponent;

/**
 * Simple login application
 *
 * @author Mike Dunbar
 */
public class LoginApplication extends Application {
    LoginComponent loginComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        loginComponent = DaggerLoginComponent.builder().build();
    }

    public LoginComponent getLoginComponent() {
        return loginComponent;
    }

}
