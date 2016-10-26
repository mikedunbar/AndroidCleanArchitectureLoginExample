package com.example.mdunbar.sampleapp;

/**
 * View for the login screen. Informs the user of any errors, progress, and navigates to the
 * landing page upon success.
 *
 * @author Mike Dunbar
 */

public interface LoginView {

    void showEmailRequiredError();

    void showPasswordRequiredError();

    void showPasswordInvalidError();

    void showEmailInvalidError();

    void showNetworkError();

    void showValidationError();

    /**
     * Show the progress dialogue and hides the form, or vice-versa
     *
     * @param showProgress if true, show progress dialogue and hide form. If false, do the opposite
     */
    void showProgress(boolean showProgress);

    void navigateToLandingPage();
}
