package com.example.mdunbar.sampleapp.login.view;

/**
 * View for the login screen. Informs the user of any errors, progress, and navigates to the
 * landing page upon success.
 *
 * @author Mike Dunbar
 */

public interface LoginView {

    void setEmail(String email);

    void setPassword(String password);

    void showEmailRequiredError();

    void showPasswordRequiredError();

    void showPasswordInvalidError();

    void showEmailInvalidError();

    void showNetworkError();

    void showValidationError();

    void showProgress();

    void hideProgress();

    void navigateToLandingPage();
}
