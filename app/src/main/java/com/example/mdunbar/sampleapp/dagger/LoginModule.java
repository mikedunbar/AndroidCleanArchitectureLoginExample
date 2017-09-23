package com.example.mdunbar.sampleapp.dagger;

import com.example.mdunbar.sampleapp.model.LoginUseCase;
import com.example.mdunbar.sampleapp.ui.login.presenter.LoginPresenter;
import com.example.mdunbar.sampleapp.ui.login.presenter.LoginPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger LoginModule
 *
 * @author Mike Dunbar
 */
@Module
class LoginModule {

    @Provides LoginUseCase provideLoginUseCase() {
        return new LoginUseCase();
    }


    @Provides static LoginPresenter provideLoginPresenter(LoginUseCase loginUseCase) {
        return new LoginPresenterImpl(loginUseCase);
    }

}
