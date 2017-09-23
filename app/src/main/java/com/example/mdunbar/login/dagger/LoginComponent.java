package com.example.mdunbar.login.dagger;

import com.example.mdunbar.login.ui.login.view.LoginActivity;

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
