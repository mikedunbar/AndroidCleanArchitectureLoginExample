package com.example.mdunbar.sampleapp.dagger;

import com.example.mdunbar.sampleapp.ui.login.view.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Dagger Login Component
 *
 * @author Mike Dunbar
 */
@Singleton
@Component(modules = LoginModule.class)
public interface LoginComponent {

    void inject(LoginActivity loginActivity);

}
