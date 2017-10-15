package com.example.mdunbar.login.dagger;

import com.example.mdunbar.login.model.LoginUseCase;
import com.example.mdunbar.login.ui.login.presenter.LoginPresenter;
import com.example.mdunbar.login.ui.login.presenter.LoginPresenterImpl;
import com.example.mdunbar.login.util.AndroidLogger;
import com.example.mdunbar.login.util.Logger;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger LoginModule
 *
 * @author Mike Dunbar
 */
@Module
class LoginModule {

    @Provides static LoginUseCase provideLoginUseCase() {
        return new LoginUseCase();
    }


    @Provides static LoginPresenter provideLoginPresenter(LoginUseCase loginUseCase, Logger logger) {
        return new LoginPresenterImpl(loginUseCase, logger);
    }

    @Provides static Logger provideLogger() {
        return new AndroidLogger();
    }

}
