package com.example.mdunbar.login;

import android.app.Application;

import com.example.mdunbar.login.dagger.DaggerLoginComponent;
import com.example.mdunbar.login.dagger.LoginComponent;

/**
 * Sample app to try out code and APIs.
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
