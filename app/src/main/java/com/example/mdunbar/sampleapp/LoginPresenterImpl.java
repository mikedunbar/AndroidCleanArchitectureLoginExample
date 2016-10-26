package com.example.mdunbar.sampleapp;

import com.google.common.base.Strings;

/**
 * Basic {@link LoginPresenter} that also listens for login results and updates the {@link LoginView}
 * with results.
 *
 * @author Mike Dunbar
 */
public class LoginPresenterImpl implements LoginPresenter, LoginResultsListener {
    private LoginView loginView;
    private LoginUseCase loginUseCase;

    /**
     * Create a new Login Presenter
     *
     * @param loginView View to update based on login results.
     * @param loginUseCase Use case to delegate core logic work to.
     */
    public LoginPresenterImpl(LoginView loginView, LoginUseCase loginUseCase) {
        this.loginView = loginView;
        this.loginUseCase = loginUseCase;
    }

    @Override
    public void doLogin(String email, String password) {
        if (Strings.isNullOrEmpty(email)) {
            loginView.showEmailRequiredError();
        } else if (Strings.isNullOrEmpty(password)) {
            loginView.showPasswordRequiredError();
        } else if (!isEmailValid(email)) {
            loginView.showEmailInvalidError();
        } else if (!isPasswordValid(password)) {
            loginView.showPasswordInvalidError();
        } else {
            loginUseCase.doLogin(email, password, this);
            loginView.showProgress(true);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    /*** LoginResultsListener ***/
    @Override
    public void onLoginSuccess() {
        loginView.navigateToLandingPage();
    }

    @Override
    public void onNetworkError() {
        loginView.showProgress(false);
        loginView.showNetworkError();
    }

    @Override
    public void onValidationError() {
        loginView.showProgress(false);
        loginView.showValidationError();
    }
}
