package com.example.mdunbar.sampleapp.model;

import android.util.Log;

import com.google.common.annotations.VisibleForTesting;

import java.util.concurrent.Callable;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Implements the login use case - here we merely simulate network access and check credentials
 * against an in-memory list.
 *
 * @author Mike Dunbar
 */
public class LoginUseCase {

    private final Scheduler subscribeOnScheduler;
    private final Scheduler observeOnScheduler;

    @VisibleForTesting
    LoginUseCase(Scheduler subscribeOnScheduler, Scheduler observeOnScheduler) {
        this.subscribeOnScheduler = subscribeOnScheduler;
        this.observeOnScheduler = observeOnScheduler;
    }

    public LoginUseCase() {
        this(Schedulers.newThread(), AndroidSchedulers.mainThread());
    }

    /**
     * Execute the login use case
     *
     * @param email           Email for login
     * @param password        Password for login
     * @param resultsListener Results listener for login
     */
    public void doLogin(final String email, final String password, final LoginResultsListener resultsListener) {
        rx.Observable<Boolean> myObservable = Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                LoginUseCase.this.logThreadState("fromCallable");
                return LoginWebService.loginUser(email, password);
            }});

        myObservable
                .subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean b) {
                        logThreadState("success callback");
                        if(b) {
                            resultsListener.onLoginSuccess();
                        } else {
                            resultsListener.onValidationError();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        logThreadState("error callback");
                        resultsListener.onNetworkError();
                    }
                });
    }

    private static class LoginWebService {
        static boolean loginUser(String email, String password) throws Exception {
            String[] DUMMY_CREDENTIALS = new String[]{
                    "foo@example.com:hello", "bar@example.com:world"};

            // Simulate network access with delay
            Thread.sleep(2000);

            boolean match = false;
            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(email)) {
                    match = pieces[1].equals(password);
                    break;
                }
            }
            return match;
        }
    }

     private void logThreadState(String desc) {
         Thread t = Thread.currentThread();
         Log.d("LoginUseCase", String.format("%s - current thread: %s", desc, t.getName()));
    }
}
