package com.example.mdunbar.sampleapp.model;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * Implements the login use case - here we merely simulate network access and check credentials
 * against a dummy in-memory list.
 *
 * @author Mike Dunbar
 */
public class LoginUseCase extends AbstractUseCase<LoginUseCase.Input, LoginUseCase.Result> {

    /**
     * Create a new instance of the login use case.
     *
     * @param performUseCaseRunner runner to perform the core use case work (background thread)
     * @param handleResultsRunner runner to perform the results handling (UI thread)
     */
    public LoginUseCase(ExecutorService performUseCaseRunner, Executor handleResultsRunner) {
        super(performUseCaseRunner, handleResultsRunner);
    }

    /**
     * Execute the login use case
     *
     * @param email Email for login
     * @param password Password for login
     * @param resultsListener Results listener for login
     */
    public void doLogin(String email, String password, LoginResultsListener resultsListener) {
        execute(new Input(email, password, resultsListener));
    }

    @Override
    protected Result performUseCase(Input input) throws Exception {
        String[] DUMMY_CREDENTIALS = new String[]{
        "foo@example.com:hello", "bar@example.com:world"};

        try {
            // Simulate network access.
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            return Result.NETWORK_ERROR;
        }

        boolean match = false;
        for (String credential : DUMMY_CREDENTIALS) {
            String[] pieces = credential.split(":");
            if (pieces[0].equals(input.email)) {
                match = pieces[1].equals(input.password);
                break;
            }
        }
        return match ? Result.SUCCESS : Result.VALIDATION_ERROR;
    }

    @Override
    protected void handleResults(Input input, Results<Result> results) {
        try {
            switch (results.get()) {
                case SUCCESS:
                    input.resultsListener.onLoginSuccess();
                    break;
                case VALIDATION_ERROR:
                    input.resultsListener.onValidationError();
                    break;
                case NETWORK_ERROR:
                    input.resultsListener.onNetworkError();
                    break;
            }
        } catch (Exception e) {
            input.resultsListener.onNetworkError();
        }
    }

    static class Input {
        final String email;
        final String password;
        final LoginResultsListener resultsListener;

        Input(String email, String password, LoginResultsListener resultsListener) {
            this.email = email;
            this.password = password;
            this.resultsListener = resultsListener;
        }
    }

    public enum Result {
        SUCCESS,
        NETWORK_ERROR,
        VALIDATION_ERROR
    }

}
