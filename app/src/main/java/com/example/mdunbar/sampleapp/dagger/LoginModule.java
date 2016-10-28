package com.example.mdunbar.sampleapp.dagger;

import android.os.Handler;
import android.os.Looper;

import com.example.mdunbar.sampleapp.HandlerExecutor;
import com.example.mdunbar.sampleapp.LoginPresenter;
import com.example.mdunbar.sampleapp.LoginPresenterImpl;
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
        return new LoginUseCase(LoginUseCase.BACKGROUND_EXECUTOR_SERVICE, new HandlerExecutor(new Handler(Looper.getMainLooper())));
    }


    @Provides static LoginPresenter provideLoginPresenter(LoginUseCase loginUseCase) {
        return new LoginPresenterImpl(loginUseCase);
    }

}
