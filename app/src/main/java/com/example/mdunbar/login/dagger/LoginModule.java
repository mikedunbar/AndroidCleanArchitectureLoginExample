package com.example.mdunbar.login.dagger;

import com.example.mdunbar.login.model.LoginUseCase;
import com.example.mdunbar.login.ui.login.presenter.LoginPresenter;
import com.example.mdunbar.login.ui.login.presenter.LoginPresenterImpl;

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
