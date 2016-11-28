package com.example.mdunbar.sampleapp.dagger;

import android.os.Handler;
import android.os.Looper;

import com.example.mdunbar.sampleapp.HandlerExecutor;
import com.example.mdunbar.sampleapp.ui.login.presenter.LoginPresenter;
import com.example.mdunbar.sampleapp.ui.login.presenter.LoginPresenterImpl;
import com.example.mdunbar.sampleapp.model.LoginUseCase;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger LoginModule
 *
 * @author Mike Dunbar
 */
@Module
public class LoginModule {

    @Provides LoginUseCase provideLoginUseCase() {
        return new LoginUseCase();
    }


    @Provides static LoginPresenter provideLoginPresenter(LoginUseCase loginUseCase) {
        return new LoginPresenterImpl(loginUseCase);
    }

}
