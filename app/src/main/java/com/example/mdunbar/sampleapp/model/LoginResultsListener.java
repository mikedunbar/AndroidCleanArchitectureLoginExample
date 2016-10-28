package com.example.mdunbar.sampleapp.model;

/**
 * Results listener companion to {@link LoginUseCase}
 *
 * @author Mike Dunbar
 */
public interface LoginResultsListener {

    /**
     * Success callback
     */
    void onLoginSuccess();

    /**
     * Network error callback
     */
    void onNetworkError();

    /**
     * Validate error callback
     */
    void onValidationError();
}
