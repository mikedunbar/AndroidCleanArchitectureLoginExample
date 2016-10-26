package com.example.mdunbar.sampleapp;

/**
 * Presenter for the Login screen.
 *
 * @author Mike Dunbar
 */

public interface LoginPresenter {

    /**
     * Attempt a login
     *
     * @param email Login email
     * @param password Login password
     */
    void doLogin(String email, String password);
}
