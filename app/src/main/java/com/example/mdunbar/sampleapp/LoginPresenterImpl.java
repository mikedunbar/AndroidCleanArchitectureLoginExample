package com.example.mdunbar.sampleapp;

import com.example.mdunbar.sampleapp.model.LoginResultsListener;
import com.example.mdunbar.sampleapp.model.LoginUseCase;
import com.google.common.base.Strings;

import javax.inject.Inject;

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
     * @param loginUseCase Use case to delegate core logic work to.
     */
    @Inject
    public LoginPresenterImpl(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    /**
     *
     * @param loginView View to update based on login results.
     */
    public void attachView(LoginView loginView) {
        this.loginView = loginView;
    }

    public void detachView() {
        this.loginView = null;
    }

    @Override
    public void doLogin(String email, String password) {
        verifyViewAttached();
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
        verifyViewAttached();
        loginView.navigateToLandingPage();
    }

    @Override
    public void onNetworkError() {
        verifyViewAttached();
        loginView.showProgress(false);
        loginView.showNetworkError();
    }

    @Override
    public void onValidationError() {
        verifyViewAttached();
        loginView.showProgress(false);
        loginView.showValidationError();
    }

    private void verifyViewAttached() {
        if (loginView == null) {
            throw new IllegalStateException();
        }
    }


}
