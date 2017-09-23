package com.example.mdunbar.login.ui.login.presenter;

import com.example.mdunbar.login.ui.login.view.LoginView;
import com.example.mdunbar.login.model.LoginResultsListener;
import com.example.mdunbar.login.model.LoginUseCase;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Strings;

import javax.inject.Inject;

/**
 * Basic {@link LoginPresenter} and {@link LoginResultsListener}, updates the {@link LoginView}
 * with login results.
 *
 * @author Mike Dunbar
 */
public class LoginPresenterImpl implements LoginPresenter, LoginResultsListener {
    @VisibleForTesting LoginView loginView;
    private LoginUseCase loginUseCase;

    private String email;
    private String password;
    @VisibleForTesting boolean loginStarted;
    @VisibleForTesting Result loginResult;

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
        loginView.setEmail(email);
        loginView.setPassword(password);

        if (!loginStarted) {
            loginView.hideProgress();
            return;
        }

        if (loginResult == null) {
            loginView.showProgress();
            return;
        }

        switch (loginResult) {
            case SUCCESS:
                onLoginSuccess();
                break;
            case NETWORK_ERROR:
                onNetworkError();
                break;
            case VALIDATION_ERROR:
                onValidationError();
                break;
        }
    }

    public void detachView() {
        this.loginView = null;
    }

    @Override
    public void doLogin(String email, String password) {
        this.email = email;
        this.password = password;
        loginStarted = false;
        loginResult = null;

        if (Strings.isNullOrEmpty(email)) {
            loginView.showEmailRequiredError();
        } else if (Strings.isNullOrEmpty(password)) {
            loginView.showPasswordRequiredError();
        } else if (!isEmailValid(email)) {
            loginView.showEmailInvalidError();
        } else if (!isPasswordValid(password)) {
            loginView.showPasswordInvalidError();
        } else {
            loginStarted = true;
            loginUseCase.doLogin(email, password, this);
            loginView.showProgress();
        }
    }

    @Override
    public void bypassLogin() {
        loginView.navigateToLandingPage();
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
        loginResult = Result.SUCCESS;
        if (loginView != null) {
            loginView.navigateToLandingPage();
        }
    }

    @Override
    public void onNetworkError() {
        loginResult = Result.NETWORK_ERROR;
        if (loginView != null) {
            loginView.hideProgress();
            loginView.showNetworkError();
        }
    }

    @Override
    public void onValidationError() {
        loginResult = Result.VALIDATION_ERROR;
        if (loginView != null) {
            loginView.hideProgress();
            loginView.showValidationError();
        }
    }

    @Override
    public void logThreadState(String desc) {
        loginView.logThreadState(desc);
    }

    enum Result {
        SUCCESS,
        NETWORK_ERROR,
        VALIDATION_ERROR
    }
}