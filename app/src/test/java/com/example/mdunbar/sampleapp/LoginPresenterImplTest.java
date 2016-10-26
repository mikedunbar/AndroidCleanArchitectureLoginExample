package com.example.mdunbar.sampleapp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterImplTest {
    @Mock private LoginView loginView;
    @Mock private LoginUseCase loginUseCase;
    private LoginPresenterImpl loginPresenter;
    private String INVALID_EMAIL = "tt_gmail.com"; // No @
    private String INVALID_PASSWORD = "abcd"; // < 5 characters
    private String VALID_EMAIL = "tt@gmail.com";
    private String VALID_PASSWORD = "abcde";

    @Before
    public void setUp() {
        loginPresenter = new LoginPresenterImpl(loginView, loginUseCase);
    }

    @Test
    public void testDoLoginWithNullEmailShowsEmailRequiredErrorOnView() {
        loginPresenter.doLogin(null, "password");
        verify(loginView).showEmailRequiredError();
    }

    @Test
    public void testDoLoginWithNullPasswordShowsPasswordRequiredErrorOnView() {
        loginPresenter.doLogin("email", null);
        verify(loginView).showPasswordRequiredError();
    }

    @Test
    public void testDoLoginWithInvalidEmailShowsInvalidEmailErrorOnView() {
        loginPresenter.doLogin(INVALID_EMAIL, VALID_PASSWORD);
        verify(loginView).showEmailInvalidError();
    }

    @Test
    public void testDoLoginWithInvalidPasswordShowsInvalidPasswordErrorOnView() {
        loginPresenter.doLogin(VALID_EMAIL, INVALID_PASSWORD);
        verify(loginView).showPasswordInvalidError();
    }

    @Test
    public void testDoLoginWithValidEmailAndPasswordDelegatesToUseCaseAndShowsProgressOnView() {
        loginPresenter.doLogin(VALID_EMAIL, VALID_PASSWORD);
        verify(loginUseCase).doLogin(VALID_EMAIL, VALID_PASSWORD, loginPresenter);
        verify(loginView).showProgress(true);
    }

    @Test
    public void testOnLoginSuccessNavigatesToLandingPageOnSuccess() {
        loginPresenter.onLoginSuccess();
        verify(loginView).navigateToLandingPage();
    }

    @Test
    public void testOnNetworkErrorHidesProgressAndShowsNetworkErrorOnView() {
        loginPresenter.onNetworkError();
        verify(loginView).showProgress(false);
        verify(loginView).showNetworkError();
    }

    @Test
    public void testOnValidationErrorHidesProgressAndShowsNetworkErrorOnView() {
        loginPresenter.onValidationError();
        verify(loginView).showProgress(false);
        verify(loginView).showValidationError();
    }
}
