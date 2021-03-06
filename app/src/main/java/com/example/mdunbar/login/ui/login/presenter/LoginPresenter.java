package com.example.mdunbar.login.ui.login.presenter;

import com.example.mdunbar.login.ui.login.view.LoginView;

/**
 * Presenter for the Login screen.
 *
 * @author Mike Dunbar
 */

public interface LoginPresenter {

    /**
     * @param loginView view to attach to presenter.
     */
    void attachView(LoginView loginView);

    /**
     * Detach the current view from the presenter. If no view currently attached, does nothing.
     */
    void detachView();

    /**
     * Attempt a login
     *
     * @param email Login email
     * @param password Login password
     */
    void doLogin(String email, String password);

    /**
     * By pass the login process, enter as a guest
     */
    void bypassLogin();
}
